package com.stackroute.ServiceProviderService;

import com.stackroute.ServiceProviderService.model.Product;
import com.stackroute.ServiceProviderService.model.ServiceProvider;
import com.stackroute.ServiceProviderService.repository.ServiceProviderRepository;
import com.stackroute.ServiceProviderService.service.ServiceProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
class ServiceProviderServiceApplicationTests {

//	@Test
//	void contextLoads() {
//	}
//
//    @Autowired
//    private ServiceProviderService service;
//
//    @MockBean
//    private ServiceProviderRepository repository;
//
//    /**
//     * Test Case to add the Service Provider
//     */
//    @Test
//    public void addServiceProviderTest() {
//        ServiceProvider serviceProvider = new ServiceProvider("101","Apple Services","appleservices@gmail.com","pass@123","9293738938");
//        when(repository.save(serviceProvider)).thenReturn(serviceProvider);
//        assertEquals(serviceProvider, service.addServiceProvider(serviceProvider));
//    }
//
//    /**
//     * Test Case to Get all the Service Providers
//     * @return the list of service providers
//     */
//    @Test
//    public void getAllServiceProviderTest() {
//        when(repository.findAll()).thenReturn(Stream.of(new ServiceProvider("101","Apple Services","appleservices@gmail.com","pass@123","9293738938"),new ServiceProvider("102","LG Services","lgservices@gmail.com","pass@123","9293738938")).collect(Collectors.toList()));
//        assertEquals(2,service.getAllServiceProviders().size());
//    }
//
//    /**
//     * Test Case to get the Service Provider by Id
//     * @return the service provider
//     */
//    @Test
//    public void getServiceProviderbyIdTest(){
//        ServiceProvider serviceProvider = new ServiceProvider("101","Apple Services","appleservices@gmail.com","pass@123","9293738938");
//        when(repository.findByUniqueId("101")).thenReturn(Optional.of(serviceProvider));
//        ServiceProvider returnserviceprovider = service.getServiceProviderById("101");
//        assertEquals(serviceProvider,returnserviceprovider);
//    }
//
//    /**
//     * Test Case to Delete the Service Provider
//     * @return the response entity
//     * */
//    @Test
//    public void deleteServiceProviderTest(){
//        ServiceProvider serviceProvider=new ServiceProvider("101","Apple Services","appleservices@gmail.com","pass@123","9293738938");
//        when(repository.findByUniqueId("101")).thenReturn(Optional.of(serviceProvider));
//        assertEquals("Service Provider is Deleted",service.deleteServiceProvider("101"));
//    }
//
//    /**
//     *  Test Case to get the Service Provider by Name and EmailId
//     * @return the service provider
//     */
//    @Test
//    public void getServiceProviderbyNameandEmailIdTest(){
//        ServiceProvider serviceProvider;
//        serviceProvider = new ServiceProvider("101","Apple Services","appleservices@gmail.com","pass@123","9293738938");
//        when(repository.getProfileByNameAndEmailId("Apple Services","appleservices@gmail.com")).thenReturn(serviceProvider);
//        ServiceProvider returnserviceprovider = service.getProfile("Apple Services","appleservices@gmail.com");
//        assertEquals(serviceProvider,returnserviceprovider);
//    }
//
//    /**
//     * Test Case to Update the Service Provider
//     * @return the response entity
//     */
//    @Test
//    public void updateServiceProviderbyIdTest(){
//        ServiceProvider serviceProvider = new ServiceProvider("101","Apple Services","appleservices@gmail.com","pass@123","9293738938");
//        ServiceProvider serviceProviderupdated = new ServiceProvider("101","Apple Services Updated","appleservices@gmail.com","pass@123","9293738938");
//        when(repository.findByUniqueId("101")).thenReturn(Optional.of(serviceProvider));
//        when(repository.save(serviceProviderupdated)).thenReturn(serviceProviderupdated);
//        assertEquals("Apple Services Updated",service.updateServiceProviderById("101",serviceProviderupdated).getName());
//    }
//
//
}
