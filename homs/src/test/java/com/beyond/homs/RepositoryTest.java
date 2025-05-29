//package com.beyond.homs;
//
////import com.beyond.homs.example.entity.Example;
////import com.beyond.homs.example.repository.ExampleRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@ActiveProfiles("test")
//public class RepositoryTest {
//    @Autowired
//    private ExampleRepository exampleRepository;
//
//    @AfterEach
//    public void tearDown() {
//        exampleRepository.deleteAll();
//    }
//
//    @Test
//    public void createAndFind() {
//        Example exampleEntity = Example.builder()
//                .name("Test")
//                .description("Desc")
//                .build();
//
//        exampleRepository.save(exampleEntity);
//
//        Example retrievedEntity = exampleRepository.findById(exampleEntity.getId()).orElse(null);
//
//        assertThat(retrievedEntity).isNotNull();
//        assertThat(retrievedEntity.getName()).isEqualTo(exampleEntity.getName());
//        assertThat(retrievedEntity.getDescription()).isEqualTo(exampleEntity.getDescription());
//    }
//
//    @Test
//    public void delete() {
//        Example exampleEntity = Example.builder()
//                .name("Test")
//                .description("Desc")
//                .build();
//        exampleRepository.save(exampleEntity);
//
//        Example retrievedEntity = exampleRepository.findById(exampleEntity.getId()).orElse(null);
//        exampleRepository.deleteById(retrievedEntity.getId());
//    }
//}
