$(document).ready(function() {
    const submitBtn = $('#submit-btn');
    const dataStorage = $('#data-storage');
    const baseUrl = getBaseUrl();

    const examId = dataStorage.data('exam-id');
    const examInProgress = dataStorage.data('exam-started');
    const examChapterId = dataStorage.data('exam-chapter-id');

    const answerEvaluationUrl = `/api/v1/exams/${examId}/answer`;
    const nextQuestionUrl = `/api/v1/exams/${examId}/question`;
    const progressUrl = `/api/v1/exams/${examId}/progress`;
    const startExamUrl = `/api/v1/exams/${examId}/start`;

    let questionCounter = 0;

    submitBtn.prop('disabled', true);

    // Check if the exam has already started
    if (examInProgress) {
        submitBtn.prop('disabled', false);
        fetchProgress();
    }
    else {
        const startExamModal = $('#start-exam-modal');
        const startExamBtn = $('#start-exam-btn');

        let startButtonClicked = false;

        // Show the modal
        startExamModal.modal('show');

        // Handle the start exam button click
        startExamBtn.on('click', function () {
            startButtonClicked = true;
            startExamModal.modal('hide');
            startExamRequest();
        });

        startExamModal.on('hidden.bs.modal', function () {
            // Redirect back to chapters
            if (!startButtonClicked) window.location.href = baseUrl + '/chapters';
        })

    }

    submitBtn.on('click', function() {
        const $btn = $(this);
        const answerField = $('#answer-field');
        const answer = answerField.val();

        // Change the button content to show loading state
        $btn.html('<span class="spinner-grow spinner-grow-sm" aria-hidden="true"></span> <span role="status">Analyzing...</span>');

        // Disable the button and the answer field
        $btn.prop('disabled', true);
        answerField.prop('disabled', true);

        // Send a request for answer evaluation
        $.ajax({
            url: answerEvaluationUrl,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ answer: answer }),
            success: function(data) {

                answerField.val('');
                answerField.prop('disabled', false);

                $btn.html('Submit');
                $btn.prop('disabled', false);

                let questionTab = $('#question-tab-' + questionCounter);
                if (!questionTab.hasClass('active')) {
                    questionTab.tab('show');
                }

                addHistoryItem(answer, data.comment, data.isCorrect);

                if (data.isCorrect) {
                    fetchProgress();
                }

            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });
    });

    function fetchNextQuestion() {
        // Send a request to fetch the next question
        $.ajax({
            url: nextQuestionUrl,
            method: 'GET',
            contentType: 'application/json',
            success: function(nextQuestionData) {
                const question = nextQuestionData.text;
                $('#question').text(question);
                createNewHistoryTab(question);
            },
            error: function(xhr, status, error) {
                console.error('Error fetching next question:', error);
            }
        });
    }

    function fetchProgress() {
        // Send a request to fetch the progress of the exam
        $.ajax({
            url: progressUrl,
            method: 'GET',
            contentType: 'application/json',
            success: function(progressData) {
                if (progressData.correctAnswers >= progressData.totalQuestions) {
                    finishExam();
                } else {
                    updateProgress(progressData.correctAnswers + 1, progressData.totalQuestions)
                    fetchNextQuestion();
                }
            },
            error: function(xhr, status, error) {
                console.error('Error fetching next question:', error);
            }
        });
    }

    function updateProgress(current, total) {
        let progressBar = document.getElementById('exam-progress');
        let percentage = (current / total) * 100;
        progressBar.style.width = percentage + '%';
        progressBar.setAttribute('aria-valuenow', percentage);
        progressBar.textContent = `Question ${current} of ${total}`;
    }

    function createNewHistoryTab(question) {
        // Create a new tab
        questionCounter++;
        const newTabId = 'question-tab-' + questionCounter;
        const newTabContentId = 'question-content-' + questionCounter;
        $('#history-tabs').append(`
            <li class="nav-item" role="presentation">
                <a class="nav-link" id="${newTabId}" data-bs-toggle="tab" data-bs-target="#${newTabContentId}" type="button" role="tab" aria-controls="home" aria-selected="true">${question}</a>
            </li>
        `);

        $('#history-content').append(`
            <div class="tab-pane fade" id="${newTabContentId}" role="tabpanel" aria-labelledby="${newTabId}">
            </div>
        `);

        // Activate the new tab
        if (questionCounter === 1) $('#' + newTabId).tab('show');
    }

    function addHistoryItem(answer, comment, isCorrect) {
        const newTabContentId = 'question-content-' + questionCounter;
        const historyItem = `
            <div class="history-item ${isCorrect ? 'correct' : 'incorrect'}">
                <div class="card">
                    <div class="card-body">
                        <p><strong>Your answer:</strong> ${answer}</p>
                        <p><strong>Examiner's comment:</strong> ${comment}</p>
                    </div>
                </div>
            </div>
        `;
        $('#' + newTabContentId).append(historyItem);
    }

    function startExamRequest() {
        $.ajax({
            url: startExamUrl,
            method: 'PUT',
            contentType: 'application/json',
            success: function() {
                startExam();
            },
            error: function(xhr, status, error) {
                console.error('Error fetching next question:', error);
            }
        });
    }

    function startExam() {
        submitBtn.prop('disabled', false);
        fetchProgress();
    }

    function finishExam() {
        const finishExamModal = $('#finish-exam-modal');

        // Fetch the unlocked chapters
        $.ajax({
            url: '/api/v1/chapters/next',
            method: 'GET',
            data: { prerequisiteChapterId: examChapterId },
            success: function(data) {
                const chaptersContainer = $('#unlocked-chapters-container');
                chaptersContainer.empty();
                if (data.length > 0) {
                    data.forEach(function(chapter) {
                        chaptersContainer.append(`
                        <a href="${baseUrl}/chapters/${chapter.id}/sections" class="chapter-item">
                            <i class="bi bi-unlock-fill icon"></i>
                            <div>
                                <div class="title">${chapter.title}</div>
                                <div class="description">${chapter.description}</div>
                            </div>
                        </a>
                    `);
                    });
                } else {
                    chaptersContainer.append('<p>No new chapters unlocked.</p>');
                }

                // Trigger confetti effect
                triggerConfetti();

                // Show the modal
                finishExamModal.modal('show');

                finishExamModal.on('hidden.bs.modal', function () {
                    // Redirect back to chapters
                    window.location.href = baseUrl + '/chapters';
                })
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });
    }

    function triggerConfetti() {
        const duration = 3 * 1000;
        const animationEnd = Date.now() + duration;
        const defaults = { startVelocity: 30, spread: 360, ticks: 60, zIndex: 2000 };

        function randomInRange(min, max) {
            return Math.random() * (max - min) + min;
        }

        const interval = setInterval(function() {
            const timeLeft = animationEnd - Date.now();

            if (timeLeft <= 0) {
                return clearInterval(interval);
            }

            const particleCount = 50 * (timeLeft / duration);
            // since particles fall down, start a bit higher than random
            confetti(Object.assign({}, defaults, { particleCount, origin: { x: randomInRange(0.1, 0.3), y: Math.random() - 0.2 } }));
            confetti(Object.assign({}, defaults, { particleCount, origin: { x: randomInRange(0.7, 0.9), y: Math.random() - 0.2 } }));
        }, 250);
    }

    function getBaseUrl() {
        const { protocol, host } = window.location;
        return `${protocol}//${host}`;
    }
});