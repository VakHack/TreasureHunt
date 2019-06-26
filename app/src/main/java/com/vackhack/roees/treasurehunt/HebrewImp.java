package com.vackhack.roees.treasurehunt;

public class HebrewImp implements LanguageImp {
    @Override
    public String createGame() {
        return "צור משחק";
    }

    @Override
    public String joinGame() {
        return "כניסת שחקן";
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

    @Override
    public String instructorClickMapPrimary() {
        return "הוסף נקודת ציון";
    }

    @Override
    public String instructorClickMapSecondary() {
        return "לחיצה על המפה תוסיף נקודה חדשה למשחק";
    }

    @Override
    public String instructorEnterRiddlePrimary() {
        return "";
    }

    @Override
    public String instructorEnterRiddleSecondary() {
        return "כאן תוכל להזין את הרמז, שמוביל את השחקנים לנקודה זו";
    }

    @Override
    public String instructorSendRiddlePrimary() {
        return "";
    }

    @Override
    public String instructorSendRiddleSecondary() {
        return "לחץ כאן לשמירת החידה";
    }

    @Override
    public String instructorClickMarkerPrimary() {
        return "תוכלו תמיד לראות את החידה ולערוך אותה";
    }

    @Override
    public String instructorClickMarkerSecondary() {
        return "נעשה זאת על ידי לחיצה על המקום בו הוכנסה";
    }

    @Override
    public String instructorDeleteMarkerPrimary() {
        return "התחרטת? לא נורא";
    }

    @Override
    public String instructorDeleteMarkerSecondary() {
        return "לאחר לחיצה על מקום החידה השמורה ניתן למחוק אותה בלחיצה על הפח";
    }

    @Override
    public String instructorLocatePrimary() {
        return "אבוד?";
    }

    @Override
    public String instructorLocateSecondary() {
        return "מצא את מיקומך הנוכחי";
    }

    @Override
    public String instructorStartGamePrimary() {
        return "הכל מוכן?";
    }

    @Override
    public String instructorStartGameSecondary() {
        return "כפתור השמירה יפיק לך קוד יחודי למשחק שלך. העתק והעבר אותו לשחקנים";
    }

    @Override
    public String playerRiddlePrimary() {
        return "ברוכים הבאים מחפשי מטמון!";
    }

    @Override
    public String playerRiddleSecondary() {
        return "כאן תוכלו לראות את החידה לנקודה הבאה במסלול";
    }

    @Override
    public String playerLocationVerifyPrimary() {
        return "חושבים שהגעתם ליעד?";
    }

    @Override
    public String playerLocationVerifySecondary() {
        return "לחצו כאן בכדי לבדוק!";
    }

    @Override
    public String playerLocatePrimary() {
        return "אבודים?";
    }

    @Override
    public String playerLocateSecondary() {
        return "מצאו את מיקומכם הנוכחי";
    }

    @Override
    public String gameSavedSuccessfully() {
        return "המשחק נשמר בהצלחה!";
    }

    @Override
    public String tryAgain() {
        return "משהו קרה. נסו שנית";
    }

    @Override
    public String allowGpsTitle() {
        return "ברוכים הבאים!";
    }

    @Override
    public String allowGpsContent() {
        return "האפליקציה מבוססת על שימוש בנתוני GPS. נא אשרו את השימוש בהם בהודעה העוקבת";
    }
}
