var data = [
    [
        "bluibiutloct01",
        "INT Patching",
        "CP-1.txt"
    ],
    [
        "co1mpbiutloct01",
        "DR Patching",
        "CP-2.txt"
    ]

]



$(document).ready(function() {
    var table = $('#example').DataTable(
  //      {data: data}

      {
        "ajax": {
            url: "/AllRecords",
            dataSrc: ''
        },
        "columnDefs": [
          { "data": "ServerName",
            "render": function( data, type, full, meta) {
                    return type === 'display' ? '<a href= "/RecordID/'+ full.RecordID + '"> <code>' + data+' </code> </a>':data;
            },
            "width": '600px',
            "targets":0},
          { "data": "SequenceName",
            "width": '600px',
            "targets":1},
          { "data": "ServerListName",
            "width": '300px',
            "targets":2}
        ]
      }
    );


    //var socket = io.connect();
    //clear data first
    //table.clear().draw();

    //socket.on('message', function (message){

    //    table.row.add( [
    //      message.serverName,
    //      message.sequenceName,
    //      message.serverList
    //    ]);


    //});

   //socket.on('endMsg', function (message){

     table.draw();
     //Prevent server side reboot from loading duplicate data
     //socket.disconnect();
   //});





});//end of big jquery
