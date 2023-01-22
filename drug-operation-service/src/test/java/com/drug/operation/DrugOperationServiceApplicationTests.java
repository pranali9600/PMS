package com.drug.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.drug.operation.model.Drug;
import com.drug.operation.repository.DrugRepository;
import com.drug.operation.service.DrugService;


@SpringBootTest
class DrugOperationServiceApplicationTests {

	@MockBean
	private DrugRepository drugRepository;
	
	@Autowired
	private DrugService drugService;
	
	private Drug drug;
	private List<Drug> drugList;
	
	/*
	 * Before each method init will be called
	 */
	@BeforeEach
	public void init() {
		drug = new Drug("1", "XYZ", "PQR", "ABC", 210.0, 40, LocalDate.of(2022, 04, 14), 
				LocalDate.of(2025, 07, 17));
		
		drugList = Arrays.asList(new Drug("1", "XYZ", "PQR", "ABC", 210.0, 40, LocalDate.of(2022, 04, 14), LocalDate.of(2025, 07, 17)),
			new Drug("2", "aasd", "ergb", "yhfg", 180.0, 50, LocalDate.of(2021, 07, 17), LocalDate.of(2024, 02, 10)));
	}
	
	// Get all drug test
	@Test
	public void getAllDrugsTest() {

		when(drugRepository.findAll()).thenReturn(drugList); //Mocking
		assertEquals(2, drugService.getDrugs().size());
		
		List<Drug> EmptyList = Collections.<Drug>emptyList();
		when(drugRepository.findAll()).thenReturn(EmptyList);
		assertEquals(0, drugService.getDrugs().size());
	
	}
	
	// Add Drug Test
//	@Test
//	public void addDrugTest()  {
//		
//		when(drugRepository.save(drug)).thenReturn(drug);
//		assertEquals(drug, drugService.addDrug(drug));
//	}
	
	// delete drug by drugID
	@Test
	public void deleteDrugByIdTest() {
		drugService.deleteDrug(drug);
		
		/*
		 * Since delete method does not return anything so we can't compare with assert.
		 * We can verify weather our method is called or not
		 */
		verify(drugRepository,times(1)).delete(drug);
	}
	
	// get drug by ID
//	@Test
//	public void getDrugByIdTest() {
//		when(drugRepository.findAll()).thenReturn(drugList);
//		long drugId = 2L;
//		
//		assertEquals(drugId, drugService.getDrugById(drugId).getId());
//		assertEquals(null, drugService.getDrugById(4L));
//	}
	
	// get Drug by name
//	@Test
//	public void getDrugByNameTest() {
//		when(drugRepository.findAll()).thenReturn(drugList);
//		String name = "XYZ";
//		
//		assertEquals(drug.getName(), drugService.getDrugByName(name).getName());
//		assertEquals(null, drugService.getDrugByName("AAA"));
//	}
	
	
	//drug update Test
//	@Test
//	public void drugUpdateTest() {
//		Drug drug1 = new Drug(1L, "XYZ", "PQR", "ABC", 210.0, 40, LocalDate.of(2022, 04, 14), 
//				LocalDate.of(2025, 07, 17));
//		
//		Drug drug2 = new Drug(null, "aasd", "ergb", "yhfg", 180.0, 50, null,null);
//		
//		Drug drug3 = new Drug(1L, "aasd", "ergb", "yhfg", 180.0, 50, LocalDate.of(2022, 04, 14),
//				LocalDate.of(2025, 07, 17));
//		
//		when(drugRepository.findById(drug1.getId())).thenReturn(drug1);
//		when(drugRepository.save(drug1)).thenReturn(drug3);
//		
//		assertEquals(drug3, drugService.updateDrugInfo(drug1.getId(), drug2));
//		
//	}

}
