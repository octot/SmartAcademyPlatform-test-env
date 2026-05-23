import { mapBackendErrors } from "./validationMapper";

export const handleFormError = (
    error,
    setErrors,
    setGlobalError
) => {

    const backendErrors =
        error?.response?.data?.errors || [];

    const {
        fieldErrors,
        globalError
    } = mapBackendErrors(backendErrors);

    setErrors(fieldErrors);
    setGlobalError(globalError);
}; 