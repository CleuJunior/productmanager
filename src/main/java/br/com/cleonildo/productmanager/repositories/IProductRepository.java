package br.com.cleonildo.productmanager.repositories;

import br.com.cleonildo.productmanager.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
}
