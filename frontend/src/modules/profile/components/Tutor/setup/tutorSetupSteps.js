import BasicInfoStep from "./steps/BasicInfoStep";
import TeachingInfoStep from "./steps/TeachingInfoStep";
import PreferencesStep from "./steps/PreferenceInfoStep";
import PaymentStep from "./steps/PaymentInfoStep";

export const tutorSetupSteps = [
  {
    id: 1,
    title: "Basic Info",
    component: BasicInfoStep
  },
  {
    id: 2,
    title: "Teaching Info",
    component: TeachingInfoStep
  },
  {
    id: 3,
    title: "Preferences",
    component: PreferencesStep
  },
  {
    id: 4,
    title: "Payment",
    component: PaymentStep
  }
];