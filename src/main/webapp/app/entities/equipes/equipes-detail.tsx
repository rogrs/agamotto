import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './equipes.reducer';
import { IEquipes } from 'app/shared/model/equipes.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEquipesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EquipesDetail extends React.Component<IEquipesDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { equipesEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="agamottoApp.equipes.detail.title">Equipes</Translate> [<b>{equipesEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nome">
                <Translate contentKey="agamottoApp.equipes.nome">Nome</Translate>
              </span>
            </dt>
            <dd>{equipesEntity.nome}</dd>
            <dt>
              <Translate contentKey="agamottoApp.equipes.departamentos">Departamentos</Translate>
            </dt>
            <dd>{equipesEntity.departamentos ? equipesEntity.departamentos.nome : ''}</dd>
          </dl>
          <Button tag={Link} to="/equipes" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/equipes/${equipesEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ equipes }: IRootState) => ({
  equipesEntity: equipes.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EquipesDetail);
