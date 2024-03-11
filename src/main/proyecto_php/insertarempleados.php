<?php
$conexion = new mysqli("localhost:3308", "root" , "" , "usuarios");
$dni =$_POST["dni"];
$nombre = $_POST["nombre"];
$apellidos = $_POST["apellido"];
$puesto = $_POST["puesto"];
$telefono =$_POST["telefono"];
$descripcion=$_POST["descripcion"];
$sql ="INSERT INTO empleados (dni, nombre,apellido,puesto,telefono,descripcion) values('$dni', '$nombre','$apellidos','$puesto','$telefono','$descripcion')";
$result= $conexion->query($sql);
if($result){
    echo "datos bien";
    
}else{
    echo "mal";
}

?>