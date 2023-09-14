package com.stackroute.ServiceProviderService.service;


import java.util.ArrayList;
import java.util.List;

import com.stackroute.ServiceProviderService.dto.NotificationDto;
import com.stackroute.ServiceProviderService.exception.EmailNotFoundException;
import com.stackroute.ServiceProviderService.exception.IdNotFoundException;
import com.stackroute.ServiceProviderService.model.Product;
import com.stackroute.ServiceProviderService.util.AppConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.ServiceProviderService.model.ServiceProvider;
import com.stackroute.ServiceProviderService.repository.ServiceProviderRepository;

/**
 * The Class ServiceProviderServiceImpl.
 *
 * @author Shivani Pakde */

@Service
public class ServiceProviderServiceImpl implements  ServiceProviderService{

	@Autowired
	ServiceProviderRepository serviceProviderRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;
	/**
	 * Get all Service Providers
	 * @return the list of Service Providers
	 */
	@Override
	public List<ServiceProvider> getAllServiceProviders() {
		
		return serviceProviderRepository.findAll();
	}
	/**
	 * Adds Service Provider in DB
	 *
	 * @return serviceprovider
	 */
	@Override
	public ServiceProvider addServiceProvider(ServiceProvider serviceprovider) {
		rabbitTemplate.convertAndSend(AppConstants.BOOKING_EXCHANGE,AppConstants.BOOKING_ROUTING_KEY,serviceprovider);
		return serviceProviderRepository.save(serviceprovider);
	}
	/**
	 * Get Service Provider by uniqueId
	 * @param uniqueId
	 * @return Service Provider
	 * */
	@Override
	public ServiceProvider getServiceProviderById(String uniqueId) {
		ServiceProvider serviceProvider = serviceProviderRepository.findByUniqueId(uniqueId).orElseThrow(()-> new IdNotFoundException("Service Provider Id not Found."));
		return serviceProvider;
	}


	/**
	 * Updates the Service Provider by uniqueId
	 * @param uniqueId
	 * @return serviceprovider
	 */
	@Override
	public ServiceProvider updateServiceProviderById(String uniqueId, ServiceProvider serviceProvider) {
		ServiceProvider existingserviceprovider = serviceProviderRepository.findByUniqueId(uniqueId).orElseThrow(()-> new IdNotFoundException("Service Provider Id is not Available"));

		if(serviceProvider.getEmailId()!=null)
			existingserviceprovider.setEmailId(serviceProvider.getEmailId());
		if(serviceProvider.getPassword()!=null)
			existingserviceprovider.setPassword(serviceProvider.getPassword());
		if(serviceProvider.getName()!=null)
			existingserviceprovider.setName(serviceProvider.getName());
		if(serviceProvider.getAddress()!=null)
			existingserviceprovider.setAddress(serviceProvider.getAddress());
		if(serviceProvider.getContactNumber()!=null)
			existingserviceprovider.setContactNumber(serviceProvider.getContactNumber());
		if(serviceProvider.getProducts()!=null)
			existingserviceprovider.setProducts(serviceProvider.getProducts());
		if(serviceProvider.getBrands()!=null)
			existingserviceprovider.setBrands(serviceProvider.getBrands());
		if(serviceProvider.getIsHomeServiceAvailable()!=null)
			existingserviceprovider.setIsHomeServiceAvailable(serviceProvider.getIsHomeServiceAvailable());
		if(serviceProvider.getSlots()!=null)
			existingserviceprovider.setSlots(serviceProvider.getSlots());
		ServiceProvider updatedserviceprovider = serviceProviderRepository.save(existingserviceprovider);
		rabbitTemplate.convertAndSend(AppConstants.BOOKING_EXCHANGE,AppConstants.BOOKING_ROUTING_KEY,updatedserviceprovider);
		//System.out.println("sent to queue");
		return updatedserviceprovider;
	}

	/**
	 * Deletes the Service Provider
	 * @param uniqueId
	 * @return response msg
	 */
	@Override
	public String deleteServiceProvider(String uniqueId) {
		ServiceProvider Provider = serviceProviderRepository.findByUniqueId(uniqueId).orElseThrow(()-> new IdNotFoundException("Service Provider Id is not Available"));
		serviceProviderRepository.delete(Provider);
		return "Service Provider is Deleted";
	}

	/**
	 * Get Profile of Service Provider
	 * @param name of Service Provider
	 * @param emailId of Service Provider
	 * @return the serviceprovider
	 */
	@Override
	public ServiceProvider getProfile(String name, String emailId) {
		ServiceProvider Provider = serviceProviderRepository.getProfileByNameAndEmailId(name,emailId).orElseThrow(()-> new EmailNotFoundException("This Combination of EmailId and Name not Found"));
		return Provider;
	}
	/**
	 * Get all products for Service Provider
	 * @param uniqueId of Service Provider
	 * @return the product list
	 */
	@Override
	public  List<Product> getAllProducts(String uniqueId) {

		ServiceProvider serviceprovider = serviceProviderRepository.findByUniqueId(uniqueId).orElseThrow(()-> new IdNotFoundException("Service Provider Id not Found."));
		List<Product> productList = serviceprovider.getProducts();
		return  productList ;
	}

	/**
	 * Add products for Service Provider
	 * @param uniqueId of Service Provider
	 * @return product
	 */
	@Override
	public Product addProduct(String uniqueId, Product product) {
		ServiceProvider serviceprovider = serviceProviderRepository.findByUniqueId(uniqueId).orElseThrow(()-> new IdNotFoundException("Service Provider Id not Found."));
		List<Product> productlist = serviceprovider.getProducts();
		if(productlist!=null && productlist.size()>0){
			productlist.add(product);
		}
		else{
			ArrayList<Product> newproductlist = new ArrayList<Product>();
			newproductlist.add(product);
			serviceprovider.setProducts(newproductlist);
		}
		updateServiceProviderById(uniqueId,serviceprovider);
		NotificationDto notificationDto= new NotificationDto();
		notificationDto.setEmail(serviceprovider.getEmailId());
		notificationDto.setMailSubject("New Product Added.");
		notificationDto.setMailBody("New product has been added in the product list with product Id as : "+product.getProductId()+ " and product name as :"+product.getName());
		System.out.println("Email sent to serviceprovider.");
		rabbitTemplate.convertAndSend(AppConstants.NOTIFICATION_EXCHANGE,AppConstants.NOTIFICATION_ROUTING_KEY,notificationDto);
		return product;
	}
	/**
	 * Get product for Service Provider
	 * @param uniqueId of Service Provider
	 * @param productId
	 * @return the product
	 */
	@Override
	public Product getProductById(String uniqueId, String productId) {

		ServiceProvider serviceprovider = serviceProviderRepository.findByUniqueId(uniqueId).orElseThrow(()-> new IdNotFoundException("Service Provider Id not Found."));
		List<Product> productlist = serviceprovider.getProducts();
		Product productObj= new Product();
			for (Product prod : productlist) {
				if (prod.getProductId().equals(productId)) {
					return prod;
				}
			}
		return productObj;
	}

	/**
	 * Update the product for Service Provider
	 * @param uniqueId of Service Provider
	 * @Param productId
	 * @return product
	 */
	@Override
	public String updateProductById(String uniqueId, String productId, Product product) {
		ServiceProvider serviceprovider = serviceProviderRepository.findByUniqueId(uniqueId).orElseThrow(()-> new IdNotFoundException("Service Provider Id not Found."));
		List<Product> productlist = serviceprovider.getProducts();
		Product matchedProduct = productlist.stream().filter(prod -> productId.equals(prod.getProductId()))
				.findAny().orElseThrow(()-> new IdNotFoundException("Product Not Found"));

		if(product.getName()!=null)
			matchedProduct.setName(product.getName());
		if(product.getCategory()!=null)
			matchedProduct.setCategory(product.getCategory());
		if(product.getBrand()!=null)
			matchedProduct.setBrand(product.getBrand());

		serviceprovider.setProducts(productlist);
		serviceProviderRepository.save(serviceprovider);
		NotificationDto notificationDto= new NotificationDto();
		notificationDto.setEmail(serviceprovider.getEmailId());
		notificationDto.setMailSubject("Product Updated.");
		notificationDto.setMailBody("Product with product Id as : "+product.getProductId()+ " and product name as :"+product.getName()+" is Updated .");
		rabbitTemplate.convertAndSend(AppConstants.NOTIFICATION_EXCHANGE,AppConstants.NOTIFICATION_ROUTING_KEY,notificationDto);
		return  "Product Updated Successfully";
	}


	/**
	 * Delete product for Service Provider
	 * @param uniqueId of Service Provider
	 * @param  productId
	 * @return the product
	 */
	@Override
	public String deleteProductById(String uniqueId, String productId) {
		ServiceProvider serviceprovider = serviceProviderRepository.findByUniqueId(uniqueId).orElseThrow(()-> new IdNotFoundException("Service Provider Id not Found."));
		List<Product> productlist = serviceprovider.getProducts();
		Product matchedProduct = productlist.stream().filter(prod -> productId.equals(prod.getProductId()))
				.findAny().orElseThrow(()-> new IdNotFoundException("Product Not Found"));
		productlist.remove(matchedProduct);
		serviceProviderRepository.save(serviceprovider);
		NotificationDto notificationDto= new NotificationDto();
		notificationDto.setEmail(serviceprovider.getEmailId());
		notificationDto.setMailSubject("Product Deleted.");
		notificationDto.setMailBody("Product with product Id as : "+productId+ " is Deleted.");
		rabbitTemplate.convertAndSend(AppConstants.NOTIFICATION_EXCHANGE,AppConstants.NOTIFICATION_ROUTING_KEY,notificationDto);
		return "Product has been deleted.";
	}
}
