<?php
$conexion = new mysqli("localhost:3308", "root" , "" , "web1");
$result = array();
$result['datos'] = array(); // Inicializar como un array vacío
$select= "SELECT * FROM usuario";
$mostrar = $conexion->query($select);

// Verificar si la consulta devolvió algún resultado
if ($mostrar !== false) {
    while($row = mysqli_fetch_array($mostrar))
    {
        $index['usuario'] = $row['0'];
        $index['contrasena'] = $row['1'];
        
        // Concatenar usuario y contraseña con "&" como separador
        $cadena = "usuario: ".$index['usuario'] . "&contrasena: " . $index['contrasena'];
        array_push($result['datos'], $cadena);

        // Agregar un separador solo si hay más de una fila
        if (mysqli_num_rows($mostrar) > 1) {
            array_push($result['datos'], "-");
        }
    }
}

echo json_encode($result);
mysqli_close($conexion);
?>
