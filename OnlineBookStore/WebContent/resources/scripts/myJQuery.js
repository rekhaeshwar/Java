/**
 * 
 */
var selectedBook=[],origdata=[],dataCount=0,selectedBookData=[],bookCount=0;
function displayTable(data)
{
origdata[dataCount]=data;
dataCount++;
}
var count =0,placeholder=0;
$(".addtocart").live("click",function(){
	var id= $(this).attr('id');
	var quantity=0, found=false;
selectedBook[count]=id;
count ++;
for(i=0;i<selectedBookData.length;i++)
	{
	if(selectedBookData[i].id==id)
		{
		selectedBookData[i].quantity=selectedBookData[i].quantity + 1;
		placeholder=i;
		found=true;
		}
	}
if(found==false)
	{
	for(j=0;j<origdata.length;j++)
	{
	if(id==origdata[j][0].id)
		{
		//placeholder=j;
		selectedBookData[bookCount]=origdata[j][0];
		selectedBookData[bookCount].quantity=1;
		}
	}
	}

if(found)
	{
	$('#cartcontent tr').each(function (i, el) {
        var tds = $(this).find('td');
        if( tds.eq(0).text()==selectedBookData[placeholder].title)
        	{
        	 tds.eq(1).text(selectedBookData[placeholder].quantity);	
        	 //var newPrice=
        	 tds.eq(2).text((origdata[placeholder][0].price*selectedBookData[placeholder].quantity).toFixed(2));	
        	}
	});
	}
if(!found){
$('#cartcontent tr:last').after('<tr><td align="right" >'+selectedBookData[bookCount].title+'</td><td>'+selectedBookData[bookCount].quantity+'</td><td>'+selectedBookData[bookCount].price+'</td><td><input type="button" class="remove" value="remove" id="'+selectedBookData[bookCount].id+'"/> </td></tr>');
bookCount++;
}
}); 

function displayCart()
{
	$('#cartcontent tr:last').after('<tr><td align="right" >'+data[i].author+'</td><td>'+data[i].title+'</td><td><input type="button" class="addtocart" value="Add to cart'+data[i].id+'" id="'+data[i].id+'"/> </td>');

}

$(".remove").live("click",function(){
	var id= $(this).attr('id');
	  $(this).parent().parent().remove();
	  var length=selectedBookData.length, afterRemove=[],count=0;
	  for(j=0;j<selectedBookData.length;j++)
		{
		if(id!=selectedBookData[j].id)
			{
			afterRemove[count]=selectedBookData[j];
			}
		}
	  selectedBookData=[];
	  for(i=0;i<afterRemove.length;i++)
		  {
		  selectedBookData[i]=afterRemove[i];
		  }
	  bookCount=afterRemove.length;
});