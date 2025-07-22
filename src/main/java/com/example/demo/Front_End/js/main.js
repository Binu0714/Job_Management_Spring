
$("#saveJobBtn").click(function () {
    let jobTitle = $("#jobTitle").val();
    let companyName = $("#companyName").val();
    let jobLocation = $("#jobLocation").val();
    let jobType = $("#jobType").val();
    let jobDescription = $("#jobDescription").val();
    let status = "Active";

    let job = {
        jobTitle: jobTitle,
        company: companyName,
        location: jobLocation,
        type: jobType,
        jobDescription: jobDescription,
        status: status
    };

    $.ajax({
        url: "http://localhost:8080/api/v2/job/create",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(job),
        success: function (response) {
            alert("Job added successfully!");
            location.reload();
        },
        error: function (xhr, status, error) {
            alert("Error adding job: " + error);
        }
    });
});