<%-- 
    Document   : index
    Created on : Mar 4, 2025, 9:25:50 PM
    Author     : TNO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en"><head>
        
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/blue.css">
        <title>ZaloPay Gateway | Quy Cách</title>


        <style>
            body {
                padding:20px;
                color:#293C56;
            }

            .icheckbox_flat-blue, .iradio_flat-blue {
                top:-2px;
                margin-right:5px;
            }
            .txtGray {
                color:#798594;
            }
            .bank-group {
                margin-left: 30px;
                max-width: 850px;
            }
            a, a:hover, a:visited, a:link {
                text-decoration: none!important;
                color:#293C56;
            }
            a.bank-item {
                display: inline-block;
                width:180px;
                height:48px;
                padding:10px;
                border-radius:4px;
                border:2px solid #f1f1f1;
                position:relative;
                vertical-align: top;
                margin-right:10px;
                margin-bottom:15px;
                font-size:13px;
            }

            a.bank-item .checkmark {
                display:none;
                width:20px;
                height: 20px;
            }
            a.bank-item.selected .checkmark {
                display: block;
                position: absolute;
                top:-10px;
                right:-10px;
                margin-right:0;
            }
            a.bank-item.selected, a.bank-item:hover  {
                border:2px solid #04BE04;
            }

            a.bank-item img {
                vertical-align: middle!important;
                display:inline-block;
                margin-right:5px;
            }
        </style>
    </head>
    <body>
        <h1 style="color: red" >Tung dep trai nhat vu tru</h1>
        <p>Vui lòng chọn hình thức thanh toán:</p>
        <div class="mb-1">
            <label><input type="radio" name="iCheck" class="iradio_flat-blue"> Ví <img src="images/logo-zalopay.svg" alt=""></label>
        </div>
        <div class="mb-1">
            <label><input type="radio" name="iCheck" class="iradio_flat-blue"> Visa, Mastercard, JCB <span class="txtGray">(qua cổng ZaloPay)</span></label>
        </div>
        <div class="mb-1">
            <label><input type="radio" name="iCheck" class="iradio_flat-blue" checked> Thẻ ATM <span class="txtGray">(qua cổng ZaloPay)</span></label>
        </div>

        <div class="bank-group">
            <a href="#" class="bank-item">
                <img src="images/bank-vtb.svg" alt=""> Vietinbank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-varb.svg" alt=""> Agribank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-vcb.svg" alt=""> Vietcombank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-bidv.svg" alt=""> BIDV
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-dab.svg" alt=""> Đông Á Bank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-scb.svg" alt=""> Sacombank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-acb.svg" alt=""> ACB
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-mb.svg" alt=""> MBBank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-tcb.svg" alt=""> Techcombank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-vpb.svg" alt=""> VPBank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-eib.svg" alt=""> Eximbank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-vib.svg" alt=""> VIB
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-hdb.svg" alt=""> HDBank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-ojb.svg" alt=""> Oceanbank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-shb.svg" alt=""> SHB
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-msb.svg" alt=""> Maritime Bank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-seab.svg" alt=""> SeABank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-abb.svg" alt=""> ABBank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-tpb.svg" alt=""> TPBank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-sgcb.svg" alt=""> TMCP Sài Gòn
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-lpb.svg" alt=""> Liên Việt Post Bank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-sgb.svg" alt=""> SaigonBank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-ocb.svg" alt=""> OCB
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-nab.svg" alt=""> Nam Á Bank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-vab.svg" alt=""> Việt Á Bank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-bvb.svg" alt=""> Bảo Việt Bank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-gpb.svg" alt=""> GPBank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-bab.svg" alt=""> Bắc Á Bank
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

            <a href="#" class="bank-item">
                <img src="images/bank-vccb.svg" alt=""> Ngân hàng Bản Việt
                <img src="images/check-mark.svg" alt="" class="checkmark">
            </a>

        </div>

        <!-- Optional JavaScript --> 
        <!-- jQuery first, then Popper.js, then Bootstrap JS --> 
        <script src="js/jquery.min.js"></script> 
        <script src="js/bootstrap.min.js"></script>
        <script src="js/icheck.min.js"></script>  

        <script>
            $(document).ready(function () {
                $('input').iCheck({
                    checkboxClass: 'icheckbox_flat-blue',
                    radioClass: 'iradio_flat-blue'
                });


                $(".bank-item").click(function () {
                    $(".bank-item").removeClass("selected");
                    $(this).addClass("selected");
                });
            });
        </script>

    </body>
</html>