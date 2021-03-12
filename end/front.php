<html>

<body style = "background-color:#dce7f3;">
<style>
  INPUT[type=submit] {
   font-weight: bold;
   display:block;
   margin: 0 auto;
}
</style>

<h1 style = "font-family:Arial; text-align:center; font-style:italic;">Rate the following movies to create a recommendation for you!</h1>

<form action="" method="post">

  <table style = "margin-left: auto; margin-right: auto;">
    <tr>
      <th>Movie</th>
      <th>Rating</th>
    </tr>

    <tr>
      <td>
         <a href="http://www.imdb.com/title/tt0808510/"> Tooth Fairy
      </td>
      <td>
        <input type="radio" name="0808510" value="0">0</input>
        <input type="radio" name="0808510" value="1">1</input>
        <input type="radio" name="0808510" value="2">2</input>
        <input type="radio" name="0808510" value="3">3</input>
        <input type="radio" name="0808510" value="4">4</input>
        <input type="radio" name="0808510" value="5">5</input>
        <input type="radio" name="0808510" value="6">6</input>
        <input type="radio" name="0808510" value="7">7</input>
        <input type="radio" name="0808510" value="8">8</input>
        <input type="radio" name="0808510" value="9">9</input>
        <input type="radio" name="0808510" value="10">10</input>
      </td>
    </tr>

    <tr>
      <td>
        <a href="http://www.imdb.com/title/tt1060277/"> Cloverfield
      </td>
      <td>
        <input type="radio" name="1060277" value="0">0</input>
        <input type="radio" name="1060277" value="1">1</input>
        <input type="radio" name="1060277" value="2">2</input>
        <input type="radio" name="1060277" value="3">3</input>
        <input type="radio" name="1060277" value="4">4</input>
        <input type="radio" name="1060277" value="5">5</input>
        <input type="radio" name="1060277" value="6">6</input>
        <input type="radio" name="1060277" value="7">7</input>
        <input type="radio" name="1060277" value="8">8</input>
        <input type="radio" name="1060277" value="9">9</input>
        <input type="radio" name="1060277" value="10">10</input>
      </td>
    </tr>

    <tr>
      <td>
        <a href="http://www.imdb.com/title/tt3713166/"> Unfriended
      </td>
      <td>
        <input type="radio" name="3713166" value="0">0</input>
        <input type="radio" name="3713166" value="1">1</input>
        <input type="radio" name="3713166" value="2">2</input>
        <input type="radio" name="3713166" value="3">3</input>
        <input type="radio" name="3713166" value="4">4</input>
        <input type="radio" name="3713166" value="5">5</input>
        <input type="radio" name="3713166" value="6">6</input>
        <input type="radio" name="3713166" value="7">7</input>
        <input type="radio" name="3713166" value="8">8</input>
        <input type="radio" name="3713166" value="9">9</input>
        <input type="radio" name="3713166" value="10">10</input>
      </td>
    </tr>

  </table>

    <input type="submit" name="submit" value="Submit"> 

</form>




<?php


$tag = false;

    if(isset($_POST['submit'])){
      if(empty($_POST['0808510'])|| empty($_POST['1060277']) || empty($_POST['3713166'])) {
          echo 'Please select the value.'; 
      } 
      else {
          echo $_POST['0808510']."\n";
          $current_timestamp1 = time(); 
          echo $_POST['1060277']."\n"; 
          $current_timestamp2 = time();
          echo $_POST['3713166']."\n"; 
          $current_timestamp3 = time();
          $tag  = true;
      }
    }
    
   
    //$current_timestamp = time();
    //echo $current_timestamp."\n";


if($tag == true){   
  $list = array (
    array(6, "0808510" ,$_POST['0808510'], $current_timestamp1),
    array(6, "1060277", $_POST['3713166'], $current_timestamp2),
    array(6, "3713166", $_POST['1060277'], $current_timestamp3)
  );

  $file = fopen("ratings_short.csv","a");

  foreach ($list as $line) {
    fputcsv($file, $line);
  }

  fclose($file);
}

?>

</body>
</html>


