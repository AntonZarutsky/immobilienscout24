"use strict";

$(document).ready(function(){
    $('input.analyze').click( onAnalyzeClick );
});


function onAnalyzeClick() {
    var url = $('#urlInput').val();

    if (validateInput(url)){
        $('#resultsContainer').addClass("hidden");
        $('#results tbody').empty();

        inputFeedback(true);
        analyze(url);
    }else{
        inputFeedback(false);
    }
}

function inputFeedback(valid) {
    var feedbackIcon = $('#urlInputFeedback');
    var feedbackDiv = $('div.has-feedback');
    if (valid){
        feedbackDiv.addClass("has-success").removeClass("has-error");
        feedbackIcon.addClass("glyphicon-ok").removeClass("glyphicon-remove");
    }else{
        feedbackDiv.addClass("has-error").removeClass("has-success");
        feedbackIcon.addClass("glyphicon-remove").removeClass("glyphicon-ok")
    }
}


function renderRow(key, value) {
    var renderedValue = value;
    if (key  == "LOGIN_FORM"){
        renderedValue = renderedValue == "0" ? "not contains" : "contains";
    }

    return "<tr><td>" + escapeHtml(key) + "</td><td>" + escapeHtml(renderedValue) + "</td></tr>";
}
function escapeHtml(unsafe) {
    return unsafe
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}

function render(msg) {
    var resultTable = $('#results tbody');
    resultTable.empty();

    Object.keys(msg.analysis)
        .forEach(function(key, i) {
            resultTable.append(renderRow(key, msg.analysis[key]));
        });

    $('#resultsContainer').removeClass("hidden")
}
function analyze(url) {
    showModel();
    $.ajax({
        type: "POST",
        url: "/analysis",
        data: {
            "pageUrl": url
        },
        success: function (msg) {
            render(msg);
            hideModel();
        },
        error: function (msg){
            alert(msg.responseText);
            hideModel();
        }
    });
}

function validateInput(url) {
    var re = /^(http[s]:\/\/){1}(www\.){0,1}[a-zA-Z0-9\.\-]+\.[a-zA-Z]{2,5}[\.]{0,1}/;
    if (!re.test(url)) {
        return false;
    }
    return true;
}

function showModel() {
    $('#myModal').removeClass("hidden").modal("show");
}
function hideModel(){
    $('#myModal').addClass("hidden").modal("hide");
}












