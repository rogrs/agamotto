import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/unidade-negocios">
      <Translate contentKey="global.menu.entities.unidadeNegocios" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/feriados">
      <Translate contentKey="global.menu.entities.feriados" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/departamentos">
      <Translate contentKey="global.menu.entities.departamentos" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/equipes">
      <Translate contentKey="global.menu.entities.equipes" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/cargos">
      <Translate contentKey="global.menu.entities.cargos" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/turnos">
      <Translate contentKey="global.menu.entities.turnos" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/colaboradores">
      <Translate contentKey="global.menu.entities.colaboradores" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/arquivos">
      <Translate contentKey="global.menu.entities.arquivos" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/linhas-arquivos">
      <Translate contentKey="global.menu.entities.linhasArquivos" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/controle-ponto">
      <Translate contentKey="global.menu.entities.controlePonto" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/ponto">
      <Translate contentKey="global.menu.entities.ponto" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
