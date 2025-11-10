package com.example.huertohogar.repository

import com.example.huertohogar.data.model.Product

class ProductRepository {
    fun getProducts(): List<Product> {
        return listOf(
            Product(
                id = "FR001",
                name = "Manzanas Fuji",
                description = "Manzanas dulces y crujientes, perfectas para un snack.",
                price = 2000.0,
                stock = 120,
                category = "Frutas Frescas",
                imageUrl = "fruta.jpg"
            ),
            Product(
                id = "FR002",
                name = "Naranjas Valencia",
                description = "Jugosas naranjas valencianas, ideales para zumos.",
                price = 1500.0,
                stock = 150,
                category = "Frutas Frescas",
                imageUrl = "https://dojiw2m9tvv09.cloudfront.net/21650/product/naranjoolindavalencia6641.png"
            ),
            Product(
                id = "FR003",
                name = "Plátanos Cavendish",
                description = "Plátanos maduros y llenos de energía.",
                price = 900.0,
                stock = 200,
                category = "Frutas Frescas",
                imageUrl = "https://bananotecnia.com/wp-content/uploads/2020/03/Banana_Filipino_Australia-800x445.jpg"
            ),
            Product(
                id = "VR001",
                name = "Zanahorias Orgánicas",
                description = "Zanahorias frescas y orgánicas, ricas en vitaminas.",
                price = 1100.0,
                stock = 90,
                category = "Verduras Orgánicas",
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-_pUnLplCnOte65UAzR72GP0kdAFtNM4_QQ&s"
            ),
            Product(
                id = "VR002",
                name = "Espinacas Frescas",
                description = "Espinacas tiernas y frescas, perfectas para ensaladas.",
                price = 1300.0,
                stock = 70,
                category = "Verduras Orgánicas",
                imageUrl = "https://www.conasi.eu/blog/wp-content/uploads/2023/07/recetas-con-espinacas-1.jpg"
            ),
            Product(
                id = "VR003",
                name = "Pimientos Tricolores",
                description = "Pimientos de colores rojo, amarillo y verde, llenos de sabor.",
                price = 1800.0,
                stock = 100,
                category = "Verduras Orgánicas",
                imageUrl = "https://www.huleymantel.com/uploads/s1/44/54/25/alba-67.webp"
            ),
            Product(
                id = "PO001",
                name = "Miel Orgánica",
                description = "Miel pura de abejas, cosechada de forma sostenible.",
                price = 5500.0,
                stock = 50,
                category = "Productos Orgánicos",
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR6n3ZY46VZWXBiMbb7Ey2j28lHspmZ5j7ftA&s"
            ),
            Product(
                id = "PO003",
                name = "Quinua Orgánica",
                description = "Grano de quinua orgánica, una fuente de proteína completa.",
                price = 4500.0,
                stock = 60,
                category = "Productos Orgánicos",
                imageUrl = "https://cloudinary.images-iherb.com/image/upload/f_auto,q_auto:eco/images/now/now06311/l/13.jpg"
            ),
            Product(
                id = "PL001",
                name = "Leche Entera",
                description = "Leche fresca y entera de vaca, ideal para toda la familia.",
                price = 1000.0,
                stock = 90,
                category = "Lácteos",
                imageUrl = "https://mercadovecinos.cl/cdn/shop/files/Screenshot2025-01-21at09.02.25.png?v=1750263915&width=480"
            )
        )
    }
}
