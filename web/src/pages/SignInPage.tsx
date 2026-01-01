import Button from "@/components/Button";
import Card from "@/components/Card";
import { FieldInput } from "@/components/FieldInput";
import { usePageContext } from "@/contexts/PageContext";
import SignInAction, { ISignInRes } from "@/utils/actions/SignInAction";
import SessionUtil from "@/utils/SessionUtil";
import {
  createForm,
  FieldValues,
  required,
  SubmitHandler,
} from "@modular-forms/solid";
import { useAction, useNavigate } from "@solidjs/router";
import { onMount, type Component } from "solid-js";

const SignInPage: Component = () => {
  const [form, { Form, Field }] = createForm<ISignInForm>();
  const signIn = useAction(SignInAction().action);
  const navigate = useNavigate();
  const sessionUtil = SessionUtil();
  const pageContext = usePageContext();

  const handleSubmit: SubmitHandler<ISignInForm> = async (values) => {
    const res = await signIn(values);
    if (res.ok) {
      sessionUtil.putSignIn(res.data as ISignInRes);
      navigate("/dashboard", { replace: true });
    }
  };

  onMount(() => {
    pageContext.action.setEditMode(true);
  });

  return (
    <section class="flex-1 flex items-center justify-center">
      <div class="mx-4 w-full md:w-1/3">
        <Card title="Sign In to your account">
          <Form onSubmit={handleSubmit}>
            <fieldset class="grid grid-cols-1 gap-4" disabled={form.submitting}>
              <Field
                name="username"
                validate={[required("Username is required!")]}>
                {(field, props) => (
                  <FieldInput
                    {...field}
                    {...props}
                    type="text"
                    label="Username"
                    required
                  />
                )}
              </Field>
              <Field
                name="password"
                validate={[required("Password is required!")]}>
                {(field, props) => (
                  <FieldInput
                    {...field}
                    {...props}
                    type="password"
                    label="Password"
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
