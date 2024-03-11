<?php
$conexion = new mysqli("localhost:3308", "root" , "" , "usuarios");
$dniv = $_POST["dniv"];
$dni =$_POST["dni"];
$nombre = $_POST["nombre"];
$apellidos = $_POST["apellido"];
$puesto = $_POST["puesto"];
$telefono =$_POST["telefono"];
$descripcion=$_POST["descripcion"];
$sql = "UPDATE empleados SET `dni`='$dni', `nombre`='$nombre', `apellido`='$apellidos', `puesto`='$puesto', `telefono`='$telefono', `descripcion`='$descripcion' WHERE dni = '$dniv'";

$result= $conexion->query($sql);

if($result){
    echo "datos bien";
    
}else{
    echo "mal";
}

?>