-- SQL para actualizar las imágenes con fotos reales y profesionales
-- Copia y pega esto en tu consola de base de datos o en el archivo SQL
UPDATE productos
SET imagen_url = 'https://images.unsplash.com/photo-1560806887-1e4cd0b6bcdb?auto=format&fit=crop&w=400&h=300&q=80',
    categoria = 'frutas'
WHERE descripcion LIKE '%Manzana%';
UPDATE productos
SET imagen_url = 'https://images.unsplash.com/photo-1586201375761-83865001e31c?auto=format&fit=crop&w=400&h=300&q=80',
    categoria = 'granos'
WHERE descripcion LIKE '%Arroz%';
UPDATE productos
SET imagen_url = 'https://images.unsplash.com/photo-1559591937-e68fb3305ff4?auto=format&fit=crop&w=400&h=300&q=80',
    categoria = 'higiene'
WHERE descripcion LIKE '%Pasta%';
UPDATE productos
SET imagen_url = 'https://images.unsplash.com/photo-1582722872445-4195a0ef3f2f?auto=format&fit=crop&w=400&h=300&q=80',
    categoria = 'lacteos'
WHERE descripcion LIKE '%Huevos%';
UPDATE productos
SET imagen_url = 'https://images.unsplash.com/photo-1550583724-125581fedade?auto=format&fit=crop&w=400&h=300&q=80',
    categoria = 'lacteos'
WHERE descripcion LIKE '%Leche%';
UPDATE productos
SET imagen_url = 'https://images.unsplash.com/photo-1509440159596-0249088772ff?auto=format&fit=crop&w=400&h=300&q=80',
    categoria = 'panaderia'
WHERE descripcion LIKE '%Pan%';
UPDATE productos
SET imagen_url = 'https://images.unsplash.com/photo-1604503468506-a8da13d82791?auto=format&fit=crop&w=400&h=300&q=80',
    categoria = 'carnes'
WHERE descripcion LIKE '%Pechuga%';
UPDATE productos
SET imagen_url = 'https://images.unsplash.com/photo-1552767059-ce182ead6c1b?auto=format&fit=crop&w=400&h=300&q=80',
    categoria = 'lacteos'
WHERE descripcion LIKE '%Queso%';
UPDATE productos
SET imagen_url = 'https://images.unsplash.com/photo-1571771894821-ad9b58a3340e?auto=format&fit=crop&w=400&h=300&q=80',
    categoria = 'frutas'
WHERE descripcion LIKE '%Banana%';
UPDATE productos
SET imagen_url = 'https://images.unsplash.com/photo-1613478223719-2ab802602423?auto=format&fit=crop&w=400&h=300&q=80',
    categoria = 'bebidas'
WHERE descripcion LIKE '%Jugo%';