package com.example.roees.treasurehunt;

public class HebrewImp implements LanguageImp {
    @Override
    public String createGame() {
        return "צור משחק";
    }

    @Override
    public String joinGame() {
        return "הצטרף למשחק";
    }

    @Override
    public String instructorEntrance() {
        return "כניסת מפעיל";
    }

    @Override
    public String enterGameCode() {
        return "הכנס קוד משחק";
    }

    @Override
    public String enterPassword() {
        return "הכנס סיסמא";
    }

    @Override
    public String enterEmail() {
        return "הכנס אימייל";
    }

    @Override
    public String submit() {
        return "כניסה";
    }

    @Override
    public String alreadyLogged() {
        return "כבר התחברת";
    }

    @Override
    public String successfullyLoggedIn() {
        return "התחברת בהצלחה!";
    }

    @Override
    public String successfullyRegistered() {
        return "רישום בוצע בהצלחה!";
    }

    @Override
    public String addNewRiddle() {
        return "הכנס חידה כאן";
    }

    @Override
    public String riddleAddedSuccessfully() {
        return "חידה נוספה בהצלחה!";
    }

    @Override
    public String gameLoadedSuccessfully() {
        return "משחק נטען בהצלחה";
    }

    @Override
    public String newGameCodeTitle() {
        return "קוד ההצטרפות למשחק";
    }

    @Override
    public String copyGameCodeButton() {
        return "העתק ללוח";
    }

    @Override
    public String gameCodeCopiedSuccessfully() {
        return "קוד הועתק בהצלחה!";
    }

    @Override
    public String OKButton() {
        return "אישור";
    }

    @Override
    public String riddleTitle() {
        return "חידה מספר ";
    }

    @Override
    public String IAmHere() {
        return "הגעתי";
    }

    @Override
    public String cannotFindLocation() {
        return "לא מצליח למצוא את מיקומך, אנא נסה שנית";
    }

    @Override
    public String notInLocation() {
        return "עדיין לא שם!";
    }

    @Override
    public String wrongCode() {
        return "קוד שגוי, נסה שנית";
    }

    @Override
    public String congratulations() {
        return "כל הכבוד!";
    }

    @Override
    public String finishedSuccessfully() {
        return "האוצר שלך";
    }
}
