<?php
$conexion = new mysqli("localhost:3308", "root" , "" , "usuarios");
$usuario =$_POST["usuario"];
$contrasena = $_POST["contrasena"];
$nombre = $_POST["nombre"];
$apellidos = $_POST["apellidos"];
$telefono =$_POST["telefono"];
$sql ="INSERT INTO usuariostabla (usuario, contrasena,nombre,apellidos,telefono) values('$usuario', '$contrasena','$nombre','$apellidos','$telefono')";
$result= $conexion->query($sql);
if($result){
    echo "datos bien";
    
}else{
    echo "mal";
}

?>