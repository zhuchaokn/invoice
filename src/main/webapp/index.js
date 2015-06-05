// 用高级浏览器打开  http://qunar.it/user/?do=card
// 进入自己的考勤，将日期选上
// 目前类似于国庆节这样的节日，非周末但是是国定假日的数据不能拿下来
var OVERTIME = 11.5,            // 加班时间
    WEEKOVERTIME = 4,           // 周末加班起始时间
    OVERMONEY = 50,             // 加班每日餐费
    EATPERSON = 1,              // 吃饭同行人数
    FROM = '威亚大厦',           // 用于渲染出租车起始地点
    TO = "安宁庄",               // 用于渲染出租车终点地点
    USE = '回家',                // 出租车用途
    REMARK = '周末加班',         // 周末加班备注
    GETONSOCPE = [5,15],        // 上车时间随机范围
    WAITING = [5,15],           // 行车等待时间
    HIGHPRICE = [70,72],        // 出租车价格
    LOWPRICE = [60,60];         // 出租车价格

var trs = $("#ems_table tbody tr"),
    data = [];

function getRandom(array){
    return parseInt(Math.random()*(array[1] - array[0]) + array[0], 10);
}

function convert2digit(str){
    return str <10 ? "0"+str : str;
}

function formatTime(date){
    var date = new Date(date);
    return date.getFullYear()+"-"+convert2digit(date.getMonth()+1)+"-"+convert2digit(date.getDate())+" "+date.getHours()+":"+convert2digit(date.getMinutes());
}

trs.each(function(index, item){
    var tds = $(item).find('td'),
        date = tds.eq(3).text(),
        workTimes = parseFloat(tds.eq(6).text()),
        weekDay = (new Date(date)).getDay(),
        endTime = tds.eq(5).text(),
        isWeekend = weekDay === 0 || weekDay === 6;
    diffTimes = isWeekend ? WEEKOVERTIME : OVERTIME;
    if(workTimes > diffTimes){
        var price = 1;
        if(isWeekend && workTimes > WEEKOVERTIME * 2){
            price = 2;
        }
        if(GETONSOCPE.length === 2){
            var _endTime = +new Date(endTime);
            _endTime += (getRandom(GETONSOCPE) * 60000);
            var getOn = formatTime(_endTime)
        }
        if(WAITING.length === 2){
            var waiting = getRandom(WAITING);
        }
        if(HIGHPRICE.length === 2){
            var highPrice = getRandom(HIGHPRICE);
        }
        if(LOWPRICE.length === 2){
            var lowPrice = getRandom(LOWPRICE);
        }
        var obj = {
            'price' : price * OVERMONEY,
            'date' : date,
            'eatperson' : EATPERSON,
            'beginTime' : tds.eq(4).text(),
            'endTime' : endTime,
            'from' : FROM,
            'to' : TO,
            'use' : USE,
            'getOn' : getOn,
            'waiting' : waiting,
            'highPrice' : highPrice,
            'lowPrice' : lowPrice
        }
        if(isWeekend){
            obj['remark'] = REMARK;
        }
        data.push(obj);
    }
})

var _data = [];
data.map(function(item,index,array){
    ++index
    _data.push(item);
    if(index % 5 === 0 || index === data.length){
        console.log(1)
        $.ajax({
            url:'https://localhost:8443/invoice/upload',
            type: 'GET',
            dataType: 'JSONP',
            cache: false,
            data: {
                userId: 'karyn',
                data: JSON.stringify(_data)
            },
            jsonp: 'callBack',
            success: function(){
                console.log('success')
            }
        });
        _data = [];
    }
})

// 复制数据

// 打开OA，调用模板然后将上面复制的数据粘贴，并赋值给data
var data = '赋值的数据';
// 执行以下代码，自动渲染出餐费
var domHash = {
        '#rtable1' : {
            date : 0,
            eatperson : 3,
            price : 4,
            averagePrice : 5,
            times : 7,
            remark : 8
        },
        '#rtable0' : {
            date : 0,
            price : 7,
            times : 6,
            from : 1,
            to : 2,
            use : 4,
            remark : 8,
            geton : 3
        }
    },
    iframes = $("#contentFrame").contents().find("#LeftRightFrameSet").contents().eq(3).contents().eq(3).contents().eq(1)[0].contentDocument.firstChild;

data = JSON.parse(data);
iframes = $(iframes);

var dataLen = data.length;

function addTrs(dom){

    function addValue(dom, index, data){
        if(index === 8){
            dom.eq(index).find('textarea').val(data);
        }else{
            dom.eq(index).find('input').val(data);
        }
    }

    data.map(function(item, index, Array){
        var _trs = iframes.find(dom).find("tr").eq(index),
            _tds = _trs.find('td');
        addValue(_tds, domHash[dom]['date'], data[index]['date']);
        if(dom === '#rtable1'){
            addValue(_tds, domHash[dom]['eatperson'], data[index]['eatperson']);
            addValue(_tds, domHash[dom]['averagePrice'], data[index]['price']);
            addValue(_tds, domHash[dom]['price'], data[index]['price']);
        }else if(dom === '#rtable0'){
            addValue(_tds, domHash[dom]['from'], data[index]['from']);
            addValue(_tds, domHash[dom]['to'], data[index]['to']);
            addValue(_tds, domHash[dom]['use'], data[index]['use']);
            addValue(_tds, domHash[dom]['price'], data[index]['taxiPrice']);
            addValue(_tds, domHash[dom]['geton'], data[index]['getOn']);
        }
        if(data[index]['remark']){
            addValue(_tds, domHash[dom]['remark'], data[index]['remark']);
        }
        $.ajax({
            type: "get",
            url: 'http://oa.corp.qunar.com/seeyon/getWorkTime?date=' + data[index]['date'],
            success: function(_data){
                var _data = JSON.parse(_data);
                addValue(_tds, domHash[dom]['times'], _data.data.hours);
            }
        });
        if(index !== dataLen - 1){
            _trs.click();
            iframes.find("#addImg").click();
        }
    })
}

function fillIn(dom){
    var dom = dom;
    addTrs(dom);
}

// 调用渲染出租车
fillIn("#rtable0");
// 调用渲染餐费
fillIn("#rtable1");
