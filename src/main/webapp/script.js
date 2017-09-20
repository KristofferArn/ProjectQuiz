$("#table").DataTable({
    ajax: {
        url: "rest/quizzes",
        dataSrc: ""
    },
    columns: [
        {data: "name"},
        {data: "startDate"}
    ]
});