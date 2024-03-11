<?php
$conexion = new mysqli("localhost:3308", "root" , "" , "usuarios");
$result = true;
$dni =$_GET["dni"];


$select = "SELECT * FROM empleados WHERE  dni = '$dni' ";
$mostrar = $conexion->query($select);

if($mostrar->num_rows > 0){
    $result = true;
}else {
    $result = false;
}

echo json_encode(array("registros" =>$result));
echo $result;

?>