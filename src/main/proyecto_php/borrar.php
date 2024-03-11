<?php
$conexion = new mysqli("localhost:3308", "root" , "" , "usuarios");
$result = true;
$dni =$_POST["dni"];

$borrar = "DELETE FROM empleados WHERE  dni = '$dni'";
$eliminar = $conexion->query($borrar);

?>