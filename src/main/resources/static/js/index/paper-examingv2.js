$(function () {
    var dbName = Window.fileId;
    var examing = {
        initial: function initial() {
            this.bindNaviBehavior();
            this.updateSummery();
            this.bindQuestionFilter();
            this.bindfocus();
            this.bindFinishOne();
            this.initMarker();
        },
        /**
         * 完成一道题触发的function
         */
        bindFinishOne: function bindFinishOne() {
            $(document).on("change", ".question input[type=radio]", function () {
                var valarr = [];
                var name = this.name;
                $("input[name='" + name + "']:checked").each(
                    function () {
                        valarr.push(this.value);
                    }
                );
                vo = {};
                vo.q = name;
                vo.a = valarr.join("");
                vo.t = 'r';
                doSave(vo);

                var current_index = $("li.question").index($(this).parent().parent().parent().parent());
                $($("a.question-navi-item")[current_index]).addClass("pressed");
                $(this).parent().parent().find(".question-list-item-selected").removeClass("question-list-item-selected");
                $(this).parent().addClass("question-list-item-selected");
            });

            $(document).on("change", ".question input[type=checkbox]", function () {
                var valarr = [];
                var name = this.name;
                $("input[name='" + name + "']:checked").each(
                    function () {
                        valarr.push(this.value);
                    }
                );
                vo = {};
                vo.q = name;
                // 排序
                var arr = valarr.sort();
                vo.a = arr.join("");
                vo.t = 'c';
                doSave(vo);

                var current_question = $(this).parent().parent().parent().parent();
                var current_index = $("li.question").index(current_question);
                var checkedboxs = current_question.find("input[type=checkbox]:checked");
                if (checkedboxs.length > 0) {
                    $($("a.question-navi-item")[current_index]).addClass("pressed");
                } else {
                    $($("a.question-navi-item")[current_index]).removeClass("pressed");
                }
                if ($(this).parent().hasClass("question-list-item-selected")) {
                    $(this).parent().removeClass("question-list-item-selected");
                } else {
                    $(this).parent().addClass("question-list-item-selected");
                }
            });

            $(document).on("change", ".question textarea,input[type='text']", function () {
                vo = {};
                vo.q = this.name;
                vo.a = $.trim($(this).val());
                vo.t = 't';
                doSave(vo);

                var current_index = $("li.question").index($(this).parent().parent().parent().parent());
                if ($(this).val() != "") {
                    $($("a.question-navi-item")[current_index]).addClass("pressed");
                } else {
                    $($("a.question-navi-item")[current_index]).removeClass("pressed");
                }
            });

        },
        bindNaviBehavior: function bindNaviBehavior() {
            var nav = $("#question-navi");
            var naviheight = $("#question-navi").height() - 33;
            var bottompx = "-" + naviheight + "px;";
            var scrollBottomRated = $("footer").height() + 2 + 100 + naviheight;
            $("#exampaper-footer").height($("#question-navi").height());
            nav.css({
                position: 'fixed',
                bottom: '0px',
                "z-index": '1'
            });
            $(document).on("click", "#question-navi-controller", function () {
                var scrollBottom = document.body.scrollHeight - $(window).scrollTop() - $(window).height();
                var nav = $("#question-navi");
                var attr = nav.attr("style");
                if (nav.css("position") == "fixed") {
                    if (nav.css("bottom") == "0px") {
                        nav.css({
                            bottom: "-" + naviheight + "px"
                        });
                    } else {
                        nav.css({
                            bottom: 0
                        });
                    }
                }
            });
        },
        /**
         * 更新题目简介信息
         */
        updateSummery: function updateSummery() {
            if ($(".question").length === 0) {
                return false;
            }
            var questiontypes = this.questiontypes;
            var summery = "";
            var alllength = 0;
            for (var i = 0; i < questiontypes.length; i++) {
                var question_sum_q = $("." + questiontypes[i].code).length;
                if (question_sum_q == 0) {
                    continue;
                } else {
                    summery = summery + "<span class=\"exampaper-filter-item efi-" + questiontypes[i].code + "\">"
                        + questiontypes[i].name + "[<span class=\"efi-tno\">"
                        + $("." + questiontypes[i].code).length + "</span>]<span class=\"efi-qcode\" style=\"display:none;\">"
                        + questiontypes[i].code + "</span></span>";
                    alllength = Number($("." + questiontypes[i].code).length) + Number(alllength);
                }
            }
            if (summery) {
                summery = "<span class=\"exampaper-filter-item efi-all\">全部[<span class=\"efi-tno\">" + alllength + "</span>]<span class=\"efi-qcode\" style=\"display:none;\" >qt-all</span></span>" + summery;
            }
            $("#exampaper-desc").html(summery);
            examing.doQuestionFilt($($(".exampaper-filter-item")[0]).find(".efi-qcode").text());
        },
        questiontypes: new Array({
            "name": "单选题",
            "code": "qt-singlechoice"
        }, {
            "name": "多选题",
            "code": "qt-multiplechoice"
        }, {
            "name": "判断题",
            "code": "qt-trueorfalse"
        }, {
            "name": "填空题",
            "code": "qt-fillblank"
        }, {
            "name": "简答题",
            "code": "qt-shortanswer"
        }, {
            "name": "论述题",
            "code": "qt-essay"
        }, {
            "name": "分析题",
            "code": "qt-analytical"
        }),
        /**
         * 绑定考题focus事件(点击考题导航)
         */
        bindfocus: function bindfocus() {
            $(document).on("click", "a.question-navi-item ", function () {
                var clickindex = $("a.question-navi-item").index(this);
                var questions = $("li.question");
                var targetQuestion = questions[clickindex];
                var targetQuestionType = $(questions[clickindex]).find(".question-type").text();
                examing.doQuestionFilt("qt-" + targetQuestionType);
                examing.scrollToElement($(targetQuestion), 0, -100);
            });
        },
        scrollToElement: function scrollToElement(selector, time, verticalOffset) {
            time = typeof (time) != 'undefined' ? time : 500;
            verticalOffset = typeof (verticalOffset) != 'undefined' ? verticalOffset : 0;
            element = $(selector);
            offset = element.offset();
            offsetTop = offset.top + verticalOffset;
            $('html, body').animate({
                scrollTop: offsetTop
            }, time);
        },
        /**
         * 切换考题类型事件
         */
        bindQuestionFilter: function bindQuestionFilter() {
            $(document).on("click", "span.exampaper-filter-item", function () {
                var qtype = $(this).find(".efi-qcode").text();
                examing.doQuestionFilt(qtype);
            });
        },
        /**
         *切换到指定的题型
         */
        doQuestionFilt: function doQuestionFilt(questiontype) {
            if ("qt-all" == questiontype) {
                var questions = $("li.question");
                questions.hide();
                $(".exampaper-filter-item").each(function () {
                    $("#exampaper-body ." + $(this).find(".efi-qcode").text()).show();
                });
                $(".exampaper-filter-item").removeClass("efi-selected");
                $("#exampaper-desc .efi-all").addClass("efi-selected");
            } else {
                if ($("#exampaper-desc .efi-" + questiontype).hasClass("efi-selected")) {
                    return false;
                } else {
                    var questions = $("li.question");
                    questions.hide();
                    $("#exampaper-body ." + questiontype).show();
                    $(".exampaper-filter-item").removeClass("efi-selected");
                    $("#exampaper-desc .efi-" + questiontype).addClass("efi-selected");
                }
            }
        },
        initMarker: function initMarker() {
            $(document).on("click", ".question-mark", function () {
                if ($(this).find(".fa-bookmark").length > 0) {
                    $(this).html("<i class=\"fa fa-bookmark-o\">");
                    var current_index = $("li.question").index($(this).parent().parent());
                    $($("a.question-navi-item")[current_index]).removeClass("question-navi-item-marked");
                } else {
                    $(this).html("<i class=\"fa fa-bookmark\">");
                    var current_index = $("li.question").index($(this).parent().parent());
                    $($("a.question-navi-item")[current_index]).addClass("question-navi-item-marked");
                }
            });
            var markhtml = "<span class=\"question-mark\" title=\"Marked as uncertain\"><i class=\"fa fa-bookmark-o\"></span>";
            $(".question-title").append(markhtml);
        }
    };

    examing.initial();

    function doError(msg, jqXHR, textStatus, errorThrown) {
        if (errorThrown != 'abort') {
            var e = [];
            e.push(msg);
            e.push('[status:');
            e.push(jqXHR.status);
            e.push(',readyState:');
            e.push(jqXHR.readyState);
            e.push(',statusText:');
            e.push(jqXHR.statusText);
            e.push(',textStatus:');
            e.push(textStatus);
            e.push(',errorThrown:');
            e.push(errorThrown);
            e.push(',responseText:');
            e.push(jqXHR.responseText);
            e.push("]")
            alert(e.join(""));
        }
    }

    function Set() {
        this.__data = {};
        this.__size = 0;
    }

    Set.prototype = {
        add: function (k, v) {
            if (!this.contains(k)) {
                this.__size++;
            }
            this.__data[k] = v;
        },
        remove: function (id) {
            if (this.__data[k]) {
                delete this.__data[k];
                this.__size--;
            }
        },
        contains: function (id) {
            if (this.__data[id]) {
                return true;
            }
            return false;
        },
        getSize: function () {
            return this.__size;
        },
        getData: function () {
            return this.__data;
        },
        clear: function () {
            this.__data = {};
            this.__size = 0;
        }
    }

    function openLocalDB(dbConfig) {
        try {//防止浏览器不支持有js bug
            var request = window.indexedDB.open(dbConfig.name, 1);
            request.onerror = function (e) {
                //console.log(e.currentTarget.error.message);
            };
            request.onsuccess = function (e) {
                dbConfig.db = e.target.result;
                dbConfig.ready = true;
            };
            request.onupgradeneeded = function (e) {
                var db = e.target.result;
                if (!db.objectStoreNames.contains(dbConfig.tableName)) {
                    db.createObjectStore(dbConfig.tableName, {keyPath: dbConfig.keyPath});
                }
                dbConfig.ready = true;
            };
        } catch (err) {
        }
    }

    function deleteLocalDB(dbConfig) {
        try {//防止浏览器不支持有js bug
            window.indexedDB.deleteDatabase(dbConfig.name);
        } catch (err) {
        }
    }

    function saveToLocalDB(dbConfig, dataArray) {
        if (dbConfig.ready) {
            var transaction = dbConfig.db.transaction(dbConfig.tableName, dbConfig.transactionType);
            var store = transaction.objectStore(dbConfig.tableName);
            for (var i = 0; i < dataArray.length; i++) {
                store.put(dataArray[i]);
            }
        }
    }

    //缓存库
    var cacheDB = new Set();
    var localDB = {
        name: dbName,
        tableName: 'exam',
        ready: false,
        isSynCache: false,//是否已从cache同步数据
        keyPath: "q",
        transactionType: 'readwrite',
        db: null
    };

    //打开数据库,这个过程是异步的什么时候打开无法保证,在打开之前数据先保存到cacheDB中
    openLocalDB(localDB);

    function doSave(vo) {
        if (localDB.ready) {//如果localDB可用
            //打开数据库过程是异步的,什么时候打开无法保证,在打开之前数据先保存到cacheDB中
            //在数据库可用的时候先把缓存中数据拷贝到数据库并设置isSynCache 为true 表示数据已同步
            if (!localDB.isSynCache) {
                saveToLocalDB(localDB, cacheDB.getData());
                localDB.isSynCache = true;
            }
            //保存当前数据到数据库
            saveToLocalDB(localDB, [vo]);
        }
        //保存当前数据到缓存
        cacheDB.add(vo.q, vo);
    }

    //交卷按钮
    $(document).on("click", "#btCommitConfirm", function () {
        commitPaper(0);
    });

    // 提交试卷
    function commitPaper(flag) {
        //cacheDB 和localDB 数据是一致的
        var saveArr = [];
        if (cacheDB.getSize() > 0) {
            var data = cacheDB.getData();
            for (k in data) {
                saveArr.push(data[k]);
            }
        }

        var dataStr = JSON2.stringify(saveArr);
        var oldMsg = $('#btCommit').html();
        $.ajax({
                type: "POST"
                , url: "../examPaper/checkAnswer?fileId=" + dbName
                , data: dataStr
                , dataType: 'json'
                , contentType: 'application/json; charset=UTF-8'
                , async: true
                , beforeSend: function () {
                    $('#btCommit').html("交卷中...").attr({"disabled": true});
                    $(".question textarea,input").attr({"disabled": true});
                }
                , complete: function (result, textStatus) {
                }
                , success: function (result, status) {
                    if (result.success) {
                        // 清空本地数据库
                        deleteLocalDB(localDB);
                        // 去查看分数页面
                        window.location.replace("../../html/scoreList.html");
                    } else {
                        alert(result.message);
                        window.close();
                    }
                }
            }
        );
    }

});//end ready




