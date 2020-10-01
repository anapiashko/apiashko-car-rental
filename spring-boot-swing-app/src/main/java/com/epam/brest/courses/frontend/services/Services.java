package com.epam.brest.courses.frontend.services;

import com.epam.brest.courses.model.Car;
import com.epam.brest.courses.model.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Services {
	public static final int TYPE_CAR = 1;
	public static final int TYPE_ORDER= 2;
	public static final int TYPE_SALESORDER = 3;
	public static final String BASE_URI = "http://localhost:8088";
	
	public static Object save(Object object, int objectType) {

		//TODO by the candidate 
		/*
		 * This method is called eventually after you click save on any edit screen
		 * object parameter is the return object from calling method guiToObject on edit screen
		 * and the type is identifier of the object type and may be TYPE_PRODUCT ,
		 * TYPE_CUSTOMER or TYPE_SALESORDER 
		 */
		final RestTemplate restTemplate = new RestTemplate();
		System.out.println("Current object : " + object);
		switch (objectType){
			case  TYPE_CAR:
				return restTemplate.postForObject(
						String.format("%s/cars", BASE_URI),
						object,
						Car.class
				);
			case TYPE_ORDER:
				System.out.println("Order : " + String.format("%s/orders", BASE_URI));
				return restTemplate.postForObject(
						String.format("%s/orders", BASE_URI),
						object,
						Order.class
				);
			default:
				return null;
		}
	}
	public static Object readRecordByCode(String code,int objectType)
	{
		//TODO by the candidate
		/*
		 * This method is called when you select record in list view of any entity
		 * and also called after you save a record to re-bind the record again
		 * the code parameter is the first column of the row you have selected
		 * and the type is identifier of the object type and may be TYPE_PRODUCT ,
		 * TYPE_CUSTOMER or TYPE_SALESORDER */
		final RestTemplate restTemplate = new RestTemplate();

		switch (objectType){
			case  TYPE_CAR:
				return restTemplate.getForObject(
						String.format("%s/cars/?id=%s", BASE_URI, code),
						Car.class
				);
			default:
				return null;
		}
	}
	public static boolean deleteRecordByCode(String code,int objectType)
	{
		//TODO by the candidate
		/*
		 * This method is called when you click delete button on an edit view
		 * the code parameter is the code of (Customer - PRoduct ) or order number of Sales Order
		 * and the type is identifier of the object type and may be TYPE_PRODUCT ,
		 * TYPE_CUSTOMER or TYPE_SALESORDER
		 */
		final RestTemplate restTemplate = new RestTemplate();

		switch (objectType){
			case  TYPE_CAR: {
				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				HttpEntity<Car> entity = new HttpEntity<>(headers);
				return restTemplate.exchange(
						BASE_URI + "/" + code, HttpMethod.DELETE, entity, Boolean.class
				).getBody();
			}
			default:
				return false;
		}
	}
	
	public static List<Object> listCurrentRecords(int objectType)
	{
		//TODO by the candidate
		/*
		 * This method is called when you open any list screen and should return all records of the specified type
		 */
		final RestTemplate restTemplate = new RestTemplate();

		switch (objectType){
			case  TYPE_CAR:
				return restTemplate
						.getForObject(
								String.format("%s/cars/filter/2020-01-01", BASE_URI),
								List.class
						);
			case TYPE_ORDER:
				return restTemplate
						.getForObject(
								String.format("%s/customer/all", BASE_URI),
								List.class
		     			);
			default:
				return new ArrayList<Object>();
		}
	}
//	public static List<ComboBoxItem> listCurrentRecordRefernces(int objectType)
//	{
//		//TODO by the candidate
//		/*
//		 * This method is called when a Combo Box need to be initialized and should
//		 * return list of ComboBoxItem which contains code and description/name for all records of specified type
//		 */
//		final RestTemplate restTemplate = new RestTemplate();
//
//		switch (objectType){
//			case  TYPE_CAR:
//				return restTemplate
//						.getForObject(
//								String.format("%s/product/all", BASE_URI),
//								ProductList.class)
//						.getProducts()
//						.stream()
//						.map(product -> new ComboBoxItem(
//								String.valueOf(product.getCode()),
//								product.getDescription()))
//						.collect(Collectors.toList());
//			case TYPE_ORDER:
//				return restTemplate
//						.getForObject(
//								String.format("%s/customer/all", BASE_URI),
//								CustomerList.class)
//						.getCustomers()
//						.stream()
//						.map(customer -> new ComboBoxItem(
//								String.valueOf(customer.getCode()),
//								customer.getName()))
//						.collect(Collectors.toList());
//			default:
//				return new ArrayList<ComboBoxItem>();
//		}
//
//	}
//	public static double getProductPrice(String productCode) {
//		//TODO by the candidate
//		/*
//		 * This method is used to get unit price of product with the code passed as a parameter
//		 */
//		final RestTemplate restTemplate = new RestTemplate();
//		Product product = restTemplate.getForObject(
//				String.format("%s/product/?code=%s", BASE_URI, productCode),
//				Product.class
//		);
//		return product.getPrice();
//	}
}
