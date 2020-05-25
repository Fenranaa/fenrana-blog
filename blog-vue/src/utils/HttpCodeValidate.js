import { Message } from "element-ui";
export const httpCodeValidate = (res, callback) => {
  if (res.data.code === 200) {
    callback();
  } else {
    Message.error({ message: res.data.msg });
  }
};
