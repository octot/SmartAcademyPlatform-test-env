export const mapBackendErrors = (errors = []) => {

    const fieldErrors = {};
    let globalError = "";

    errors.forEach((error) => {

        if (error.field) {
            fieldErrors[error.field] = error.message;
        } else {
            globalError = error.message;
        }

    });

    return {
        fieldErrors,
        globalError
    };
};