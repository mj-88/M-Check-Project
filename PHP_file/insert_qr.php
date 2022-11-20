<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('db_qr.php');

    //안드로이드 연결
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android)
    {

        $room=$_POST['room'];
        $seat=$_POST['seat'];
        $student_id=$_POST['student_id'];

        if(empty($room)){
            $errMSG = "강의실을 입력하세요";
        }
        else if(empty($seat)){
            $errMSG = "좌석을 입력하세요";
        }
        else if(empty($student_id)){
            $errMSG = "학번을 입력하세요";
        }

        if(!isset($errMSG))
        {
            try{
                $stmt = $con->prepare('INSERT INTO c_0715(room, seat, student_id) VALUES(:room, :seat, :student_id)');
                $stmt->bindParam(':room', $room);
                $stmt->bindParam(':seat', $seat);
                $stmt->bindParam(':student_id', $student_id);

                if($stmt->execute())
                {
                    $successMSG = "출석 성공";
                }
                else
                {
                    $errMSG = "출석 실패하였습니다.";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }

    }
?>
  <?php 
        if (isset($errMSG)) echo $errMSG;
        if (isset($successMSG)) echo $successMSG;
       
        $android = strpos($_SERVER['HTTP_USER_AGENT'],"Android");

        if(!$android){
    ?>


<html>
   <body>
      
        
        <form action="<?php $_PHP_SELF ?>" method="POST">
            room :  <input type = "text" name = "room" />
            seat :  <input type = "text" name = "seat" />
            student id :  <input type = "text" name = "student_id" />
            <input type = "submit" name = "submit" />
        </form>
   
   </body>
</html>

<?php
        }
?>
