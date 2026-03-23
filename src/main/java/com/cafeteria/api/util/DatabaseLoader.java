package com.cafeteria.api.util;

import com.cafeteria.api.entity.Categoria;
import com.cafeteria.api.entity.Producto;
import com.cafeteria.api.entity.Rol;
import com.cafeteria.api.entity.Usuario;
import com.cafeteria.api.repository.CategoriaRepository;
import com.cafeteria.api.repository.ProductoRepository;
import com.cafeteria.api.repository.RolRepository;
import com.cafeteria.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {

        if (categoriaRepository.count() == 0) {

            Categoria cafe = categoriaRepository.save(new Categoria(null, "Café"));
            Categoria kits = categoriaRepository.save(new Categoria(null, "Kits"));
            Categoria accesorios = categoriaRepository.save(new Categoria(null, "Accesorios"));


            // --- CATEGORÍA: CAFÉ EN GRANO (6 productos) ---
            productoRepository.save(new Producto(null, "Blend de la Casa (250g)", "Notas de chocolate y nueces", new BigDecimal("25.00"), 50, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774240871/cafe-blend_ec3muy.jpg", true, cafe));
            productoRepository.save(new Producto(null, "Café Origen Cusco (250g)", "Notas frutales y acidez brillante", new BigDecimal("32.00"), 30, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774240871/cafe-cuzco_yhpsma.jpg", true, cafe));
            productoRepository.save(new Producto(null, "Café Villa Rica (500g)", "Cuerpo medio con aroma intenso", new BigDecimal("45.00"), 20, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774240871/cafe_villarica_efhex5.jpg", true, cafe));
            productoRepository.save(new Producto(null, "Espresso Roast (250g)", "Tueste oscuro ideal para máquinas", new BigDecimal("28.00"), 40, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774240871/espressoroast_y51wy2.jpg", true, cafe));
            productoRepository.save(new Producto(null, "Descafeinado Natural (250g)", "Proceso de agua, sin químicos", new BigDecimal("30.00"), 15, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774240871/descafeinado_qyzjmj.jpg", true, cafe));
            productoRepository.save(new Producto(null, "Edición Limitada Geisha", "Café de especialidad premium", new BigDecimal("85.00"), 10, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774240896/geisha_ugspz9.jpg", true, cafe));

            // --- CATEGORÍA: KITS DE REGALO (6 productos) ---
            productoRepository.save(new Producto(null, "Kit Barista Principiante", "Prensa francesa + Café 250g", new BigDecimal("120.00"), 10, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774241777/kitbaristaPrincipiantes_ammk3w.jpg", true, kits));
            productoRepository.save(new Producto(null, "Pack Degustación Orígenes", "3 variedades de 100g c/u", new BigDecimal("75.00"), 15, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774241768/packDegustacion_vi9gv3.jpg", true, kits));
            productoRepository.save(new Producto(null, "Kit V60 Completo", "Portafiltro + Filtros + Café", new BigDecimal("150.00"), 8, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774241768/portafiltro_tnvjqb.jpg", true, kits));
            productoRepository.save(new Producto(null, "Dúo Amantes del Café", "2 tazas cerámicas + Café", new BigDecimal("95.00"), 12, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774241768/duoAmantes_bxrkmc.jpg", true, kits));
            productoRepository.save(new Producto(null, "Kit Frío (Cold Brew)", "Botella infusora + Café molido", new BigDecimal("110.00"), 10, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774241769/kitFrio_jgycde.jpg", true, kits));
            productoRepository.save(new Producto(null, "Cesta Gourmet Navideña", "Café, galletas y chocolate", new BigDecimal("180.00"), 5, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774241772/cesta_erciin.jpg", true, kits));

            // --- CATEGORÍA: ACCESORIOS (6 productos) ---
            productoRepository.save(new Producto(null, "Molino Manual Cerámico", "Molienda ajustable", new BigDecimal("85.00"), 20, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774242283/molinomanual_eymyve.jpg", true, accesorios));
            productoRepository.save(new Producto(null, "Prensa Francesa Acero", "Capacidad 600ml", new BigDecimal("65.00"), 25, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774242277/prensafrancesa_ghqv2c.jpg", true, accesorios));
            productoRepository.save(new Producto(null, "Taza Cerámica Negra", "Mantiene 6h el calor", new BigDecimal("45.00"), 30, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774242265/tazaCeramica_rbpwbm.webp", true, accesorios));
            productoRepository.save(new Producto(null, "Balanza Digital de Precisión", "Con cronómetro integrado", new BigDecimal("90.00"), 15, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774242481/balanza_zclwba.webp", true, accesorios));
            productoRepository.save(new Producto(null, "Filtros de Papel V60", "Paquete de 100 unidades", new BigDecimal("35.00"), 100, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774242482/filtros_mqysx7.jpg", true, accesorios));
            productoRepository.save(new Producto(null, "Espumador de Leche Manual", "Potencia para lattes", new BigDecimal("55.00"), 18, "https://res.cloudinary.com/dfid8iuf3/image/upload/v1774242485/espumador_nwdmfq.jpg", true, accesorios));
        }

        if (rolRepository.count() == 0) {
            Rol adminRol = rolRepository.save(new Rol(null, "ADMINISTRADOR"));
            rolRepository.save(new Rol(null, "CLIENTE"));

            if (usuarioRepository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setNombre("Juan Pérez");
                admin.setEmail("admin@gmail.com");
                admin.setContrasenia(passwordEncoder.encode("admin1234"));
                admin.setRol(adminRol);
                admin.setActivo(true);

                usuarioRepository.save(admin);
                System.out.println("Base de datos poblada con 18 productos y usuario admin.");
            }
        }
    }
}
