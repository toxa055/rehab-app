let searchButton = $('#searchByCureName');
let changeButton = $('#changeCure');

let patternUnit = $('#patternUnit');
let patternCount = $('#patternCount');
let limit = 1;
chooseCheckbox();

searchButton.click(function () {
    $.ajax({
        url: "http://localhost:8080/rest/cures/" + $('#cureName').val()
    }).done(function (cureDto) {
        $('#cureName').attr('readonly', true).val(cureDto.name)
        $('#cureId').val(cureDto.id)
        $('#cureType').val(cureDto.cureType);
        changeButton.attr('disabled', false);
        searchButton.attr('disabled', true);
        if ($('#cureType').val() === 'PROCEDURE') {
            $('#dose').attr('readonly', true);
        } else {
            $('#dose').attr('readonly', false);
        }
    })
})

changeButton.click(function () {
    if ($('#cureType').val() === 'PROCEDURE') {
        $('#dose').attr('readonly', false);
    }
    $('#cureName').attr('readonly', false).val('');
    $('#cureId').val('');
    $('#cureType').val('');
    $('#dose').val('');
    changeButton.attr('disabled', true);
    searchButton.attr('disabled', false);
})

patternUnit.change(function () {
    uncheckAndEnableAllCheckbox();
    limit = patternCount.val();
    let selectedValue = $('#patternUnit option:selected').text();
    if (selectedValue === "DAY") {
        $('#patternCount option[value="5"]').remove();
        $('#patternCount option[value="6"]').remove();
        $('#patternCount option[value="7"]').remove();
        $('#periodUnit').prepend('<option value="DAY">DAY</option>');
        if (limit > 4) {
            limit = 1;
        }
        $('.parts-of-day').show();
        $('.days-of-week').attr('hidden', true);
    }
    if (selectedValue === "WEEK") {
        $('#patternCount')
            .append('<option value="5">5</option>')
            .append('<option value="6">6</option>')
            .append('<option value="7">7</option>');
        $('#periodUnit option[value="DAY"]').remove();
        $('.parts-of-day').hide();
        $('.days-of-week').attr('hidden', false);
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
}

function chooseCheckbox() {
    $("input:checkbox").click(function () {
        let bol = $("input:checkbox:checked").length >= limit;
        $("input:checkbox").not(":checked").attr("disabled", bol);
    });
}
