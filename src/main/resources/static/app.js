$(document).ready(function () {
   var tableContents = "";
   var options = "";
   var modal = $("#details-modal");
   var productTable = $("#product-table");
   var brandOptions = $("#brand-options");
   var allData = [];
   var newProductModal = $("#add-product-form");
   var allMarkets = [
        {
            id: 1,
            region: "Alabama",
            country: "US"
        },
        {
            id: 2,
            region: "Illinois",
            country: "US"
        },
        {
            id: 3,
            region: "Minnesota",
            country: "US"
        },
        {
            id: 4,
            region: "New Mexico",
            country: "US"
        }
    ]

   // Get All Products
   $.get("http://localhost:8080/products", function(data, status) {
        console.log(data)
        allData = data.products;
       $.each(data.products, function (key, item) {
           tableContents +=
                "<tr class='row details-button' id=" +
                item.id +
                "><td><span class='brand'>" +
                item.brand.brandName +
                "</span><span class='model'>&nbsp;" +
                item.model +
                "</td><td class='quantity'>" +
                item.quantity +
                "</td></tr>";
       })
       productTable.append(tableContents);
   });
   
   $.get("http://localhost:8080/manufacturers", function(data, status) {
       console.log(data)
       options += "<select name='new-brand' id='new-brand' class='input'>";
       $.each(data, function (key, item) {
           options +=
                "<option value='" + item.id + "'>" + item.brandName + "</option>";
       })
       options +="</select>";
       console.log(options)
       brandOptions.append(options);
   });
   
   // get all markets
   var marketCheckboxList = "";

   $.each(allMarkets, function (key, item) {
           marketCheckboxList +=
               '<input type="checkbox" id=' +
               item.id +
               ' name="market" value="{&quot;region&quot;: &quot;' +
               item.region +
               '&quot;, &quot;country&quot;: &quot;' +
               item.country +
               '&quot;, &quot;id&quot;: ' +
               item.id +
               '}"><label for="' +
               item.id +
               '">' +
               item.region +
               ', ' +
               item.country +
               '</label><br />'
       })
     $("#new-markets").append(marketCheckboxList);

   // Add Product Form
   $("#add-product-form").submit(function (event) {
     event.preventDefault();
     var brand = $("#new-brand").val();
     var model = $("#new-model").val();
     var upc = $("#new-upc").val();
     var quantity = parseInt($("#new-quantity").val());
//     var markets = $("#new-markets").val()
     var marketsArr = [];
     $("input:checkbox[name=market]:checked").each(function() {
        console.log($(this).val());
        marketsArr.push(JSON.parse($(this).val()));
     })
     console.log(marketsArr)
     var selectedBrand = { id: brand}
     var newItem = {
       brand: { id: brand},
       model: model,
       upc: upc,
       quantity: quantity,
       markets: marketsArr
       }
     console.log(newItem);
     $.ajax("http://localhost:8080/products", {
        data : JSON.stringify(newItem),
        contentType : 'application/json',
        type: 'POST'
     })
     location.reload();
         $("#new-brand").val("");
         $("#new-model").val("");
         $("#new-upc").val("");
         $("#new-quantity").val("")
   });

     $("#new-button").click(function () {
       newProductModal.fadeIn(100);
       $("#backdrop").fadeIn(100);
     });

     $("#add-product-close").click(function () {
       newProductModal.fadeOut(100);
       $("#backdrop").fadeOut(100);
     });


   // Edit Modal
   $("#product-table").on("click", ".details-button", function () {
     $("#backdrop").fadeIn(100);
     modal.fadeIn(50);
     var id = $(this).attr("id");
     var obj = allData.find((item) => item.id == id);
     $("#details-brand").text(obj.brand.brandName);
     $("#details-model").text(obj.model);
     $("#details-upc").text(obj.upc);
     $("#details-quantity").text(obj.quantity);
     var marketList = '';
    console.log(obj.markets.length)
    if (obj.markets.length > 0) {
         $.each(obj.markets, function(key, item) {
            marketList += "<li class='market-item'>" +
                item.region +
                ", " +
                item.country +
                "</li>"
         })
    } else {
     marketList += '<li class="market-item">None</li>'
    }

     $("#details-markets").append(marketList);
   });

//   $("#edit-product-form").submit(function (event) {
//     event.preventDefault();
//     var editedBrand = $("#edit-brand").val();
//     var editedModel = $("#edit-model").val();
//     var editedUpc = parseInt($("#edit-upc").val());
//     var editedQuantity = parseInt($("#edit-quantity").val());
//     var editedObj = {
//       brand: editedBrand,
//       model: editedModel,
//       upc: editedUpc,
//       quantity: editedQuantity,
//     };
//     $.ajax("http://localhost:8080/product/" + )
//     console.log(editedObj);
//     $("#edit-brand").val("");
//     $("#edit-model").val("");
//     $("#edit-upc").val("");
//     $("#edit-quantity").val("");
     // ajax put call
//     $("#backdrop").fadeOut(100);
//     modal.fadeOut(100);
//   });

   $("#close-details-modal").click(function () {
   $("#details-markets").text('');
     $("#backdrop").fadeOut(100);
     modal.fadeOut(100);
   });
 });