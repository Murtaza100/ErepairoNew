package com.stackroute.BookingService;

import com.stackroute.BookingService.Repository.CustomerServiceRepository;
import com.stackroute.BookingService.Repository.ServiceProviderRepository;
import com.stackroute.BookingService.Service.ServiceProviderInterface;
import com.stackroute.BookingService.model.CustomerRequestsDetails;
import com.stackroute.BookingService.model.ServiceCenterDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.collections.CollectionUtils.collect;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookingServiceApplicationTests {

//	@MockBean
//	ServiceProviderRepository serviceProviderRepositor;
//	@MockBean
//	CustomerServiceRepository customerServiceRepository;
//	@Autowired
//	ServiceProviderInterface serviceProviderInterface;
//	@Test
//	public void GetAllServiceCenters() {
//		when(serviceProviderRepositor.findAll()).thenReturn(Stream
//				.of(new ServiceCenterDetails(50666,"Mi Service Center",981123345,"Kolkata","Sunday", 40, Arrays.asList("26 Jan","2 Oct","15 Aug"),null,null,null)).collect(Collectors.toList()));
//		assertThat(1).isEqualTo(serviceProviderInterface.ListOfAllServiceCenters().size());
//	}
//@Test
//public void getservicecenterdetailsbyid() {
//	//Optional<ServiceCenterDetails> serviceCenterDetails=Optional.of(new ServiceCenterDetails(5000,"Apple Service Center",972234402,"West Thane, Mumbai","THRUSDAY", 0, Arrays.asList("26 Jan", "2 Oct", "15 Aug", "Holi", "Diwali"),null,LocalTime.of(9,00,00), LocalTime.of(19,00,00)));
//	ServiceCenterDetails serviceCenterDetails1=new ServiceCenterDetails(5000,"Apple Service Center",972234402,"West Thane, Mumbai","THRUSDAY", 0, Arrays.asList("26 Jan", "2 Oct", "15 Aug", "Holi", "Diwali"),null,LocalTime.of(9,00,00), LocalTime.of(19,00,00));
//	when(serviceProviderRepositor.getbyid(5000)).thenReturn(serviceCenterDetails1);
//	assertThat(serviceCenterDetails1.toString()).isEqualTo(serviceProviderInterface.GetServiceCenterDetailsById(5000).toString());
//}
//	@Test
//	public void GetAllBookings() {
//		when(customerServiceRepository.findAll()).thenReturn(Stream
//				.of(new CustomerRequestsDetails(5000,"Aayush",965413556,"abc@gmail.com","Delhi","OS not working","Mobile","iphone",LocalTime.of(9,00,00), LocalDate.of(2020,01,8), LocalDateTime.now())).collect(Collectors.toList()));
//		assertThat(1).isEqualTo(serviceProviderInterface.GetAllBookings().size());
//	}





//	@Test
//	public void updateProductByIdTest() {
//		Product product=new Product(1, "Samsung", 76868.0, "Mobile");
//		Product product1=new Product(1, "Samsung j7", 76868.0, "Mobile");
//		ProductDto productDto=new ProductDto(1, "Samsung j7", 76868.0, "Mobile");
//		when(productRepository.findById(1)).thenReturn(Optional.of(product));
//		when(productRepository.save(any())).thenReturn(product1);
//		assertEquals("Samsung j7", productService.updateProductById(1, productDto).getName());
//	}
//	@Test
//	public void deleteProductById() {
//		Product product=new Product(1, "Samsung", 76868.0, "Mobile");
//		when(productRepository.findById(1)).thenReturn(Optional.of(product));
//		assertEquals("product deleetd successfully", productService.deleteProductById(1));
//	}
}
