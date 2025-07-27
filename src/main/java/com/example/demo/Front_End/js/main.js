$(document).ready(function () {
    loadJob(currentPage, pageSize);

    $("#nextPageBtn").click(function () {
        currentPage++;
        loadJob(currentPage, pageSize);
    });

    $("#prevPageBtn").click(function () {
        if (currentPage > 0) {
            currentPage--;
            loadJob(currentPage, pageSize);
        }
    });
});

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


function loadJobs() {
    $.ajax({
        url: "http://localhost:8080/api/v2/job/get",
        method: "GET",
        success: function (jobs) {
            let tbody = $("#jobsTableBody");
            tbody.empty();

            jobs.data.forEach(function (job, index) {
                let row = `
                    <tr>
                        <td>${job.id}</td>
                        <td>${job.jobTitle}</td>
                        <td>${job.company}</td>
                        <td>${job.location}</td>
                        <td>${job.type}</td>
                        <td>${job.status}</td>
                        <td>
                            <button class="btn btn-outline-primary" onclick="editStatus(${job.id})">Deactivate</button>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#editJobModal" onclick="editJob(${job.id})">Edit</button>
                            <button class="btn btn-sm btn-danger" onclick="deleteJob(${job.id})">Delete</button>
                        </td>
                    </tr>
                `;
                tbody.append(row);
            });
        },
        error: function () {
            alert("Failed to load job data.");
        }
    });
}

function editJob(id) {
    $.ajax({
        url: `http://localhost:8080/api/v2/job/get/${id}`,
        method: "GET",
        success: function (job) {
            $("#editJobId").val(job.id);
            $("#editJobTitle").val(job.jobTitle);
            $("#editCompanyName").val(job.company);
            $("#editJobLocation").val(job.location);
            $("#editJobType").val(job.type);
            $("#editJobDescription").val(job.jobDescription);
        },
        error: function () {
            alert("Failed to load job data for editing.");
        }
    });
}

$("#updateJobBtn").click(function () {
    let jobId = $("#editJobId").val();
    let jobTitle = $("#editJobTitle").val();
    let companyName = $("#editCompanyName").val();
    let jobLocation = $("#editJobLocation").val();
    let jobType = $("#editJobType").val();
    let jobDescription = $("#editJobDescription").val();
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
        url: `http://localhost:8080/api/v2/job/update/${jobId}`,
        method: "Put",
        contentType: "application/json",
        data: JSON.stringify(job),
        success: function (response) {
            alert("Job updated successfully!");
            loadJob(currentPage, pageSize);
            location.reload();
        },
        error: function (xhr, status, error) {
            alert("Error updating job: " + error);
        }
    });
});

function deleteJob(id) {
    if (!confirm("Are you sure you want to delete this job?")) return;

    $.ajax({
        url: `http://localhost:8080/api/v2/job/delete/${id}`,
        method: "DELETE",
        success: function () {
            alert("Job deleted!");
            loadJob(currentPage, pageSize);
        },
        error: function () {
            alert("Error deleting job");
        }
    });
}

function editStatus(id) {
    $.ajax({
        url: `http://localhost:8080/api/v2/job/get/${id}`,
        method: "GET",
        success: function (job) {
            if (job.status === "Deactivate"){
                alert("Unable to Continue.This job is already deactivated.");
                return;
            }

            if (!confirm("Are you sure you want to deactivate this job?")) return;

            $.ajax({
                url: `http://localhost:8080/api/v2/job/status/${id}`,
                method: "PATCH",
                success: function () {
                    alert("Job deactivated!");
                    loadJob(currentPage, pageSize);
                },
                error: function () {
                    alert("Error deactivating job");
                }
            });
        },
        error: function () {
            alert("Failed to load job data for editing.");
        }
    });
}

$('#searchInput').on('keyup', function () {
    let keyword = $(this).val();

    if (keyword === "") {
        $("#jobsTableBody").empty();
        loadJob(currentPage, pageSize);
        return;
    }

    $.ajax({
        url: `http://localhost:8080/api/v2/job/search/${keyword}`,
        method: "GET",
        success: function (jobs) {
            $("#jobsTableBody").empty();

            jobs.data.forEach(function (job) {
                let row = `
                    <tr>
                        <td>${job.id}</td>
                        <td>${job.jobTitle}</td>
                        <td>${job.company}</td>
                        <td>${job.location}</td>
                        <td>${job.type}</td>
                        <td>${job.status}</td>
                        <td>
                            <button class="btn btn-outline-primary" onclick="editStatus(${job.id})">Deactivate</button>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#editJobModal" onclick="editJob(${job.id})">Edit</button>
                            <button class="btn btn-sm btn-danger" onclick="deleteJob(${job.id})">Delete</button>
                        </td>
                    </tr>
                `;

                $("#jobsTableBody").append(row);
            });
        },
        error: function () {
            alert("Error searching jobs.");
        }
    });
});

let currentPage = 0;
let pageSize = 5;

function loadJob(page, size) {
    $.ajax({
        url: `http://localhost:8080/api/v2/job/get-paged?page=${page}&size=${size}`,
        method: "GET",
        success: function (jobs) {
            let tbody = $("#jobsTableBody");
            tbody.empty();

            if (jobs.length === 0) {
                $("#nextPageBtn").prop("disabled", true);

                if (page > 0) {
                    currentPage--;
                }
                updatePageInfo();
                return;
            }

            $("#prevPageBtn").prop("disabled", currentPage === 0);

            $("#nextPageBtn").prop("disabled", jobs.length < size);

            jobs.data.forEach(function (job) {
                let row = `
                    <tr>
                        <td>${job.id}</td>
                        <td>${job.jobTitle}</td>
                        <td>${job.company}</td>
                        <td>${job.location}</td>
                        <td>${job.type}</td>
                        <td>${job.status}</td>
                        <td>
                            <button class="btn btn-outline-primary" onclick="editStatus(${job.id})">Deactivate</button>
                        </td>
                        <td>
                            <button class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#editJobModal" onclick="editJob(${job.id})">Edit</button>
                            <button class="btn btn-danger btn-sm" onclick="deleteJob(${job.id})">Delete</button>
                        </td>
                    </tr>
                `;
                tbody.append(row);
            });
            updatePageInfo();
        },
        error: function () {
            alert("Failed to load jobs.");
        }
    });
}

function updatePageInfo() {
    $("#pageInfo").text(`Page ${currentPage + 1}`);
}