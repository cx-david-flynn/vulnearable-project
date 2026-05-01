package com.centricsoftware.poc;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class CustomerService {

	@Inject
	private CustomerRepository customerRepository;

	@Autowired
	private EntityManager em;

	public List<Customer> findByLastName(String lastName) {
		return customerRepository.findByLastName(lastName);
	}

	public Customer findById(Long id) {
		return customerRepository.findById(id).map(x -> x).orElse(null);
	}

	public Customer save(Customer customer) {
		return customerRepository.saveAndFlush(customer);
	}

	public Customer update(Customer customer) {
		boolean doesCustomerExist = customerRepository.existsById(customer.getId());
		if (doesCustomerExist) {
			return customerRepository.saveAndFlush(customer);
		}

		return null;

	}

	public Customer patchCustomerLastName(Long customerId, String lastName) {
		Optional<Customer> existingCustomer = customerRepository.findById(customerId);
		return existingCustomer.map(customer -> {
			customer.setLastName(lastName);
			return customerRepository.save(customer);
		}).orElse(null);

	}

	public Customer save(Long customerId, Customer customer) {
		if (customerRepository.existsById(customerId)) {
			return customerRepository.saveAndFlush(customer);
		}
		return null;
	}

	public List<Customer> findUsingLastName(String firstName) {
		return customerRepository.findUsingLastName2(firstName);
	}

	public List<Customer> findUsingLastNameAdd(String firstName) {
		return customerRepository.findUsingLastName3(firstName);
	}

	public List<Customer> findUsingLastNameIgnoreCase(String lastName) {
		String jpql = "SELECT u FROM Customer u WHERE u.lastName = '" + lastName + "'";
		return em.createQuery(jpql, Customer.class).getResultList();
	}

	public List<Customer> doesCustomerExistByFirstName(String firstName) {
		StringBuffer test = qbuilder(firstName);
		test.replace(5, 6, "T");

		Query notThat = em.createNativeQuery(test.toString(), Customer.class);

		return notThat.getResultList();

	}

	final String q = "SELECB ";
	final String z = "id, first_name, last_name, greet_name ";
	final String a = "Customer ";
	final String m = "from ";
	final String s = "where ";
	final String bb = "last_name = '";
	
	public List<Customer> doesCustomerExistByFirstName5(String firstName) {
		return customerRepository.findUsingLastName5(firstName);
	}

	private StringBuffer qbuilder(String value) {
		String c = value.trim();
		StringBuffer t = new StringBuffer();
		t.append(q);
		t.append(z);
		System.out.print(t.toString());
		t.append("\t");
		t.deleteCharAt(t.length() - 1);
		t.append(m);
		t.append(a);
		t.append(s);
		t.append(bb);
		t.append(c);
		t.append("'");
		return t;
	}

}
