import Edit from "@/components/edit/edit";
import TextField from "@/components/text-field/text-field";
import {useClientsContext} from "@/context/clients";
import {ClientsDetailsDataType} from "@/types";
import Box from "@mui/material/Box";
import {usePathname} from "next/navigation";
import {ChangeEvent, FormEvent, useState} from "react";
import {PiCopySimpleThin} from "react-icons/pi";
import toast, {Toaster} from "react-hot-toast";

type Props = {
  name: string;
  phone: string;
  email: string;
  id: string;
};

const MapBox = ({name, phone, email, id}: Props) => {
  const [readonly, setReadonly] = useState<boolean>(true);
  const [clientData, setClientData] = useState({
    name,
    phone,
    email,
  });
  const idParam = usePathname().split("/").pop();
  const client = useClientsContext();

  const onSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    client.setData(
      client.data.map((el) => {
        if (el.id === idParam) {
          el.contactPeople.map((cp) => {
            if (cp.name === clientData.name) {
              cp.name = clientData.name;
              cp.phone = clientData.phone;
              cp.email = clientData.email;
            }
          });
        }
        return el;
      })
    );
    setReadonly(true);
  };

  const filterData = () => {
    // console.log("details.data", client.data);
    return client.data.filter(
      (el: ClientsDetailsDataType) => el.id === idParam
    )[0];
  };
  const handleClick = (v: boolean) => {
    setReadonly(v);
    if (v) {
      const filteredData = filterData();
      console.log("filteredData", filteredData);
      const data = filteredData.contactPeople
        .filter((cp) => cp.id === id)
        .map((c) => {
          return {
            name: c.name,
            phone: c.phone,
            email: c.email,
          };
        });
      setClientData(data[0]);
    }
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const {name, value} = e.target;
    setClientData({
      ...clientData,
      [name]: value,
    });
  };
  const handleCopyToClipboard = () => {
    const {name, phone, email} = clientData;
    const text = `name: ${name},\nphone : ${phone}, \nemail: ${email}`
    navigator.clipboard.writeText(text)
      .then(() => toast.success("Successfully copied!"))
      .catch((err) => toast.error(`Ups not able to copy!`));
  }

  return (
    <div className="client-contact-details-card">
      <Box
        component="form"
        className="client-contact-details-form"
        noValidate
        autoComplete="off"
        onSubmit={(e) => onSubmit(e)}
      >
        <div className="card-edit">
          <Edit readonly={readonly} handleClick={handleClick}/>
          <PiCopySimpleThin className="card-edit__copy" onClick={handleCopyToClipboard}/>
        </div>
        <TextField
          label="Name"
          onChange={handleChange}
          value={clientData.name}
          readonly={readonly}
          name="name"
        />
        <TextField
          label="Phone"
          onChange={handleChange}
          value={clientData.phone}
          readonly={readonly}
          name="phone"
        />
        <TextField
          label="Email"
          onChange={handleChange}
          value={clientData.email}
          readonly={readonly}
          name="email"
        />
      </Box>
      <Toaster
        position="top-center"
        reverseOrder={false}
      />
    </div>
  );
};

export default MapBox;
