package br.com.cleonildo.productmanager.repositories;

import br.com.cleonildo.productmanager.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
