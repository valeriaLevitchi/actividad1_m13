<?php
$conexion = new mysqli("localhost:3308", "root" , "" , "usuarios");
$result = array();


$select= "SELECT nombre FROM empleados";
$mostrar = $conexion->query($select);

if ($mostrar->num_rows > 0) {
    // Crear un array para almacenar los datos
    $datos = array();

    // Recorrer los resultados y agregarlos al array
    while ($fila = $mostrar->fetch_assoc()) {
        $datos[] = $fila;
    }

    // Convertir el array a formato JSON
    echo json_encode($datos);
} else {
    // Si no se encontraron resultados, devolver un mensaje de error
    echo "No se encontraron registros en la tabla.";
}


// Cerrar la conexión
$conexion->close();
?>