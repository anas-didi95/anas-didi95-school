import Button from "@/components/Button";
import Card from "@/components/Card";
import FieldText from "@/components/FieldText";
import { IUserContextStore, useUserContext } from "@/contexts/UserContext";
import SignInAction from "@/utils/actions/SignInAction";
import {
  createForm,
  FieldValues,
  required,
  SubmitHandler,
} from "@modular-forms/solid";
import { useAction, useNavigate } from "@solidjs/router";
import { type Component } from "solid-js";
import { useToast } from "solid-notifications";

const SignInPage: Component = () => {
  const [form, { Form, Field }] = createForm<ISignInForm>();
  const { notify } = useToast();
  const signIn = useAction(SignInAction().action);
  const navigate = useNavigate();
  const userContext = useUserContext();

  const handleSubmit: SubmitHandler<ISignInForm> = async (values) => {
    const res = await signIn(values);
    if (res.ok) {
      userContext.action.setToken(res.data as IUserContextStore);
      navigate("/dashboard");
    } else {
      notify(res.data as string, { type: "error" });
    }
  };

  return (
    <section class="flex-1 flex items-center justify-center">
      <div class="mx-4 w-full md:w-1/3">
        <Card title="Sign In to your account">
          <Form onSubmit={handleSubmit}>
            <fieldset class="grid grid-cols-1 gap-4" disabled={form.submitting}>
              <Field
                name="username"
                validate={[required("Username is required!")]}>
                {(field, attrs) => (
                  <FieldText
                    type="text"
                    label="Username"
                    field={field}
                    attrs={attrs}
                    required
                  />
                )}
              </Field>
              <Field
                name="password"
                validate={[required("Password is required!")]}>
                {(field, attrs) => (
                  <FieldText
                    type="password"
                    label="Password"
                    field={field}
                    attrs={attrs}
                    required
                  />
                )}
              </Field>
            </fieldset>
            <div class="mt-4 flex justify-end gap-2">
              <Button label="Sign In" color="primary" type="submit" />
            </div>
          </Form>
        </Card>
      </div>
    </section>
  );
};

export default SignInPage;

interface ISignInForm extends FieldValues {
  username: string;
  password: string;
}
