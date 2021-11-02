let newCureButton = $('#cureCreateButton');

newCureButton.click(function () {
    let cureName = $('#name');
    if (cureName.val() === '') {
        cureName.attr('class', 'form-control is-invalid')
        $('#invalidCureNameModal').text('Cure name cannot be empty');
    } else if ((cureName.val().length < 3) || (cureName.val().length > 30)) {
        cureName.attr('class', 'form-control is-invalid')
        $('#invalidCureNameModal').text('Length must be from 3 to 30 symbols');
    } else {
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
    }
});
