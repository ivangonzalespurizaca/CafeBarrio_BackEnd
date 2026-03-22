package com.cafeteria.api.util;

import com.cafeteria.api.entity.Categoria;
import com.cafeteria.api.entity.Producto;
import com.cafeteria.api.repository.CategoriaRepository;
import com.cafeteria.api.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {

        if (categoriaRepository.count() == 0) {

            Categoria cafe = categoriaRepository.save(new Categoria(null, "Cafés"));
            Categoria postre = categoriaRepository.save(new Categoria(null, "Postres"));


            productoRepository.save(new Producto(null, "Espresso", "Café intenso de 30ml",
                    new BigDecimal("5.50"), 20, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774108714/espresso_uycbti.png", true, cafe));

            productoRepository.save(new Producto(null, "Cappuccino", "Café con leche espumosa",
                    new BigDecimal("8.50"), 15, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774108714/cappucino_ijpjie.jpg", true, cafe));

            productoRepository.save(new Producto(null, "Cheesecake", "Tarta de queso con frutos rojos",
                    new BigDecimal("12.00"), 10, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774108714/cheescake_e3grp7.jpg", true, postre));

            System.out.println("Datos de prueba cargados exitosamente.");
        }
    }
}
