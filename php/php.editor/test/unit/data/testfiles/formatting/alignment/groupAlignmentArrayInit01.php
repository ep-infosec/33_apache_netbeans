<?php

$var1  = 'test1';
$var11  = 'test2';
$longVarName              = 'test2';  // some comment

$short =           'test2'; // some comment
$shortli = "test";

if (true) {
    $varname= 'a text';
    $index             = 0;
}

$w = 10;

class test {

    private $field1  = 20;
    private $field22   = 22;   // line comment
    private $longnameField    = 11;    // line comment
    private     $shortField = 'ahoj';

    const TEST1            = 't';
    const LONG_CONST  = 't';

    function method() {
        $t1 = 10;
        $t2 = 20;
        $t11= '111';

        $ar = array(
            "var"=>10,
                      "longname" => 22,
            "shor" => 222
        );
    }

}

?>
