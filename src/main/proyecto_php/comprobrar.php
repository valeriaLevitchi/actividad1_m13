<?php
$conexion = new mysqli("localhost:3308", "root" , "" , "usuarios");
$result = true;
$nombre =$_GET["nombre"];
$contrasena =$_GET["contrasena"];
$select = "SELECT * FROM usuariostabla WHERE  usuario = '$nombre' AND contrasena = '$contrasena'";
$mostrar = $conexion->query($select);

if($mostrar->num_rows > 0){
    $result = true;
}else {
    $result = false;
}

echo json_encode(array("registros" =>$result));
echo $result;

?>