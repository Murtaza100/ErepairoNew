package com.stackroute.ServiceProviderService.service;

import java.util.List;
import com.stackroute.ServiceProviderService.model.Product;
import com.stackroute.ServiceProviderService.model.ServiceProvider;

/**
 * The Interface ServiceProviderService.
 *
 * @author Shivani Pakde
 */

public interface ServiceProviderService {

	List<ServiceProvider> getAllServiceProviders();
	ServiceProvider addServiceProvider(ServiceProvider serviceprovider);
	ServiceProvider getServiceProviderById(String uniqueId);
	ServiceProvider updateServiceProviderById(String uniqueId,ServiceProvider serviceProvider);
	String deleteServiceProvider(String uniqueId);
	ServiceProvider getProfile(String name,String emailId);
	//-------------------------------------------------------------------

	List<Product> getAllProducts(String uniqueId);
	Product addProduct(String uniqueId, Product product);
	Product getProductById(String uniqueId, String productId);
	String updateProductById(String uniqueId,String productId, Product product);
	String deleteProductById(String uniqueId, String productId);
}
