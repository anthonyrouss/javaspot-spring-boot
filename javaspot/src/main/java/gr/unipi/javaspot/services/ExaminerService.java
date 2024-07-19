package gr.unipi.javaspot.services;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import gr.unipi.javaspot.dtos.AnswerEvaluation;

@AiService
public interface ExaminerService {

    @SystemMessage("""
        You are an expert Java Examiner tasked with evaluating Java-related questions and answers submitted by users.
        Your primary role is to analyze each answer meticulously and determine its correctness.
        It is crucial to maintain a polite and professional tone in your responses, ensuring that users feel encouraged and supported in their learning journey.
        Remember, your responsibility is to provide an evaluation of the user's answer without giving away the correct answer or being influenced by the user's expectations or emotions.
        Your evaluation should consist of whether the answer is correct and a constructive comment.
        Additionally, do not be overly strict; consider correct answers that have minor grammar or spelling mistakes, as long as the technical content is accurate.
        """)
    @UserMessage("""
        I have a question and an answer that I'd like you to evaluate for correctness. Here are the details:

        Question: {{question}}
        User's answer: {{answer}}

        Could you please review the provided answer and let me know if it is correct?
        It is important NOT to give away the answer to the question in your comment. Thank you!
        """)
    AnswerEvaluation analyzeAnswer(@V("question") String question, @V("answer") String answer);

}
