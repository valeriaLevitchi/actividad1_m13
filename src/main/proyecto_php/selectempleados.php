<?php
$conexion = new mysqli("localhost:3308", "root" , "" , "usuarios");
$result = array();

$dni =$_GET["dni"];
$select= "SELECT * FROM empleados WHERE dni ='$dni' ";
$mostrar = $conexion->query($select);


if ($mostrar->num_rows > 0) {
    // Mostrar los datos en una tabla
    
    while($fila = $mostrar->fetch_assoc()) {
        $result = array(
            "dni" => $fila['dni'],
            "nombre" => $fila['nombre'],
            "apellido" => $fila['apellido'],
            "puesto" => $fila['puesto'],
            "telefono" => $fila['telefono'],
            "descripcion" => $fila['descripcion'],
        );
    }
    
} else {
    echo "No se encontraron resultados.";
}

echo json_encode($result);

// Cerrar la conexión
$conexion->close();
?>
