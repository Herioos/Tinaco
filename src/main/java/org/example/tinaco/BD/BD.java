package org.example.tinaco.BD;

public class BD {
/*
CREATE TABLE tabla_usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombreU_u VARCHAR(32),
    contrasena VARCHAR(32),
    recuperar VARCHAR(32),
    nombre_u VARCHAR(16),
    apellidoP_u VARCHAR(16),
    telefono_u VARCHAR(10)
);

CREATE TABLE tabla_sensores (
    id_sensor INT AUTO_INCREMENT PRIMARY KEY,
    nombre_s VARCHAR(16),
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES tabla_usuarios(id_usuario)
);

CREATE TABLE tabla_tinacos (
    id_tinaco INT AUTO_INCREMENT PRIMARY KEY,
    nombre_t VARCHAR(16),
    capacidad INT,
    id_sensor INT,
    FOREIGN KEY (id_sensor) REFERENCES tabla_sensores(id_sensor),
    nombre_s VARCHAR(16),
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES tabla_usuarios(id_usuario)
);

create table tabla_gestiones(
	id_gestion INT auto_increment primary key,
	nombre_g Varchar(16),
	nivelMaximo int(3),
	nivelMinimo int(3),
	nombre_t varchar(16),
	id_usuario INT,
	FOREIGN KEY (id_usuario) REFERENCES tabla_usuarios(id_usuario)
);

CREATE TABLE tabla_llenados (
    id_llenado INT AUTO_INCREMENT PRIMARY KEY,
    fecha_ll DATE,
    hora_ll TIME,
    cantidad_ll INT,
    id_tinaco INT,
    FOREIGN KEY (id_tinaco) REFERENCES tabla_tinacos(id_tinaco),
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES tabla_usuarios(id_usuario)
);

CREATE TABLE tabla_areas(
    id_area INT AUTO_INCREMENT PRIMARY KEY,
    fecha_ll DATE,
    hora_ll TIME,
    cantidad_ll INT,
    id_sensor INT,
    FOREIGN KEY (id_sensor) REFERENCES tabla_sensores(id_sensor),
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES tabla_usuarios(id_usuario)
);

CREATE TABLE tabla_lecturas (
    id_lectura INT AUTO_INCREMENT PRIMARY KEY,
    fecha_l DATE,
    hora_l TIME,
    cantidad_l INT,
    id_tinaco INT,
    id_usuario INT,
    FOREIGN KEY (id_tinaco) REFERENCES tabla_tinacos(id_tinaco),
    FOREIGN KEY (id_usuario) REFERENCES tabla_usuarios(id_usuario)
);

*/
}
