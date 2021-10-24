let newCureButton = $('#cureCreateButton');

newCureButton.click(function () {
    let cure = {
        name: $('#name').val(),
        cureType: $('#cureTypeModal').val()
    }
    $.ajax({
        url: "http://localhost:8080/rest/cures",
        method: "POST",
        dataType: "json",
        data: cure
    }).done(function (cureDto) {
        $('#cureName').attr('readonly', true).val(cureDto.name)
        $('#cureId').val(cureDto.id)
        $('#cureType').val(cureDto.cureType);
        changeButton.attr('disabled', false);
        searchButton.attr('disabled', true);
        if ($('#cureType').val() === 'PROCEDURE') {
            $('#dose').attr('readonly', true).val('According to instruction.');
        } else {
            $('#dose').attr('readonly', false).val('');
        }
        $('#newCureModal').modal('hide');
        $('#name').val('');
    })
});