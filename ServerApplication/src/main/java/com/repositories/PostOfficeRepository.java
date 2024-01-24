package com.repositories;

import com.domain.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostOfficeRepository extends JpaRepository<PostOffice, Long> {

   // PostOffice findPostOfficeByPostalCode(int postalCode);
}
