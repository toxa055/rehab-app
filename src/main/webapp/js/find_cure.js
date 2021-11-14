let searchButton = $('#searchByCureName');
let changeButton = $('#changeCure');
let sendFormButton = $('#sendFormButton');

let patternUnit = $('#patternUnit');
let patternCount = $('#patternCount');
let limit = 1;
chooseCheckbox();

if ($('#cureType').val() !== '') {
    $('#cureName').attr('readonly', true);
    changeButton.attr('disabled', false);
    searchButton.attr('disabled', true);
    sendFormButton.attr('disabled', false);
}

searchButton.click(function () {
    let cureName = $('#cureName');
    let cureType = $('#cureType');
    if (cureName.val() === '') {
        cureName.attr('class', 'form-control is-invalid');
        $('#invalidCureName').text('Cure name cannot be empty');
    } else if ((cureName.val().length < 3) || (cureName.val().length > 30)) {
        cureName.attr('class', 'form-control is-invalid');
        $('#invalidCureName').text('Length must be from 3 to 30 symbols');
    } else {
        $.ajax({
            url: "http://localhost:8060/rest/cures/" + $('#cureName').val()
        }).done(function (cureDto) {
            if ((cureDto == null) || (cureDto.id == null)) {
                cureNotFound(cureName);
            } else {
                cureName.attr('readonly', true).attr('class', 'form-control is-valid').val(cureDto.name);
                $('#cureId').val(cureDto.id);
                cureType.attr('class', 'form-control is-valid').val(cureDto.cureType);
                changeButton.attr('disabled', false);
                searchButton.attr('disabled', true);
                sendFormButton.attr('disabled', false);
                if (cureType.val() === 'PROCEDURE') {
                    $('#dose').attr('readonly', true).val('According to instruction.');
                } else {
                    $('#dose').attr('readonly', false);
                }
                if (patternCount.val() == $("input:checkbox:checked").length) {
                    sendFormButton.attr('disabled', false);
                } else {
                    sendFormButton.attr('disabled', true);
                }
            }
        }).fail(function () {
            cureNotFound(cureName);
        })
    }
})

changeButton.click(function () {
    if ($('#cureType').val() === 'PROCEDURE') {
        $('#dose').attr('readonly', false);
    }
    $('#cureName').attr('readonly', false).attr('class', 'form-control').val('');
    $('#cureId').val('');
    $('#cureType').attr('class', 'form-control').val('');
    $('#dose').val('');
    changeButton.attr('disabled', true);
    searchButton.attr('disabled', false);
    sendFormButton.attr('disabled', true);
})

patternUnit.change(function () {
    uncheckAndEnableAllCheckbox();
    limit = patternCount.val();
    let selectedValue = $('#patternUnit option:selected').text();
    if (selectedValue === "DAY") {
        $('#patternCount option[value="5"]').remove();
        $('#patternCount option[value="6"]').remove();
        $('#patternCount option[value="7"]').remove();
        if (limit > 4) {
            limit = 1;
        }
        $('#parts-of-day').show();
        $('#days-of-week').attr('hidden', true);
    }
    if (selectedValue === "WEEK") {
        $('#patternCount')
            .append('<option value="5">5</option>')
            .append('<option value="6">6</option>')
            .append('<option value="7">7</option>');
        $('#parts-of-day').hide();
        $('#days-of-week').attr('hidden', false);
    }
    chooseCheckbox();
})

patternCount.change(function () {
    uncheckAndEnableAllCheckbox()
    limit = patternCount.val();
    chooseCheckbox();
})

function uncheckAndEnableAllCheckbox() {
    $('input[type=checkbox]').each(function () {
        this.checked = false;
        this.disabled = false;
    });
    sendFormButton.attr('disabled', true);
}

function chooseCheckbox() {
    $("input:checkbox").click(function () {
        let bol = $("input:checkbox:checked").length >= limit;
        $("input:checkbox").not(":checked").attr("disabled", bol);
        if (patternCount.val() == $("input:checkbox:checked").length) {
            if ($('#cureType').val() !== '') {
                sendFormButton.attr('disabled', false);
            }
        } else {
            sendFormButton.attr('disabled', true);
        }
    });
}

function cureNotFound(cureName) {
    cureName.attr('class', 'form-control is-invalid');
    $('#invalidCureName').text('Cure is not found');
}
