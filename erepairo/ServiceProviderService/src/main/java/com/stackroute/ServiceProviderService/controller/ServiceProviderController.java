package com.stackroute.ServiceProviderService.controller;

import java.util.List;
import com.stackroute.ServiceProviderService.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stackroute.ServiceProviderService.model.Product;
import com.stackroute.ServiceProviderService.model.ServiceProvider;
import com.stackroute.ServiceProviderService.service.ServiceProviderService;

/**
 * The Class ServiceProviderController
 *
 * @author Shivani Pakde
 *
 */

@RestController
@RequestMapping("/erepario")
public class ServiceProviderController {

	
	@Autowired
	ServiceProviderService serviceproviderservice;

	@Autowired
	SequenceGeneratorService sequenceGeneratorService;

	/**
	 * Get all Service Providers
	 * @return the list of Service Providers
	 */

	@GetMapping("/serviceprovider")
	public ResponseEntity<List<ServiceProvider>> getAllServiceProviders(){
		return new ResponseEntity<List<ServiceProvider>>(this.serviceproviderservice.getAllServiceProviders(),HttpStatus.OK);
	}

	/**
	 * Adds Service Provider in DB
	 *
	 * @return the response entity
	 */
	@PostMapping("/serviceprovider")
	public ResponseEntity<ServiceProvider> addServiceProvider(@RequestBody ServiceProvider serviceprovider){
		serviceprovider.setUniqueId(sequenceGeneratorService.generateSequence(ServiceProvider.SEQUENCE_NAME));
		return new ResponseEntity<ServiceProvider>(this.serviceproviderservice.addServiceProvider(serviceprovider),HttpStatus.OK);
	}
	/**
	 * Get Service Provider by uniqueId
	 * @param uniqueId
	 * @return Service Provider
	 * */
	@GetMapping("/serviceprovider/{uniqueId}")
	public ResponseEntity<ServiceProvider> getServiceProviderById(@PathVariable("uniqueId")String uniqueId ){
		return new ResponseEntity<ServiceProvider>(this.serviceproviderservice.getServiceProviderById(uniqueId),HttpStatus.OK);
	}

	/**
	 * Updates the Service Provider by uniqueId
	 * @param uniqueId
	 * @return the response entity
	 */
	@PutMapping("/serviceprovider/{uniqueId}")
	public ResponseEntity<ServiceProvider> updateServiceProvider(@PathVariable("uniqueId")String uniqueId, @RequestBody ServiceProvider serviceProvider){
		return new ResponseEntity<ServiceProvider>(this.serviceproviderservice.updateServiceProviderById(uniqueId, serviceProvider),HttpStatus.OK);
	}

	/**
	 * Deletes the Service Provider
	 * @param uniqueId
	 * @return the response entity
	 */
	@DeleteMapping("/serviceprovider/{uniqueId}")
	public ResponseEntity<String> deleteServiceProviderById(@PathVariable("uniqueId")String uniqueId){
		return new ResponseEntity<String>(this.serviceproviderservice.deleteServiceProvider(uniqueId),HttpStatus.OK);
	}

	/**
	 * Get Profile of Service Provider
	 * @param name of Service Provider
	 * @param emailId of Service Provider
	 * @return the response entity
	 */
	@GetMapping("/serviceprovider/{name}/{emailId}")
	public ResponseEntity<ServiceProvider> getProfileOfServiceProvider(@PathVariable("name")String name,@PathVariable("emailId") String emailId){
		return new ResponseEntity<ServiceProvider>(this.serviceproviderservice.getProfile(name,emailId),HttpStatus.OK);
	}

	/**
	 * Get all products for Service Provider
	 * @param uniqueId of Service Provider
	 * @return the product list
	 */
   @GetMapping("/serviceprovider/product/{uniqueId}")
   public List<Product> getallProducts(@PathVariable("uniqueId") String uniqueId){
      List<Product> productsList = this.serviceproviderservice.getAllProducts(uniqueId);
	  return productsList;

   }

	/**
	 * Add products for Service Provider
	 * @param uniqueId of Service Provider
	 * @return product and send notification to Service Provider Email
	 */
	@PostMapping("/serviceprovider/product/{uniqueId}")
	public Product addProductsByuniqueId(@PathVariable("uniqueId") String uniqueId, @RequestBody Product product){
	    this.serviceproviderservice.addProduct(uniqueId,product);
		return product;
	}

	/**
	 * Get product for Service Provider
	 * @param uniqueId of Service Provider
	 * @param productId
	 * @return the response entity
	 */
	@GetMapping("/serviceprovider/{uniqueId}/product/{productId}")
    public ResponseEntity<Product> getProductByUniqueId(@PathVariable("uniqueId") String uniqueId,@PathVariable("productId") String productId){
		return new ResponseEntity<Product>(this.serviceproviderservice.getProductById(uniqueId,productId),HttpStatus.OK);
	}

	/**
	 * Update the product for Service Provider
	 * @param uniqueId of Service Provider
	 * @Param productId
	 * @return Response msg and sends notification email to the service provider
	 */
	@PutMapping("/serviceprovider/{uniqueId}/product/{productId}")
	public String updateProductByuniqueId(@PathVariable("uniqueId") String uniqueId,@PathVariable("productId") String productId, @RequestBody Product product){
		return this.serviceproviderservice.updateProductById(uniqueId,productId,product);
	}

	/**
	 * Delete product for Service Provider
	 * @param uniqueId of Service Provider
	 * @param  productId
	 * @return response msg
	 */
	@DeleteMapping("/serviceprovider/{uniqueId}/product/{productId}")
	public String deleteProductByuniqueId(@PathVariable("uniqueId")String uniqueId,@PathVariable("productId")String productId){
		return this.serviceproviderservice.deleteProductById(uniqueId,productId);
	}

}
